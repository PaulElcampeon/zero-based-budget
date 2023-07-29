var expenses = [];
const expenseList = document.getElementById("expense-list")
const expenseInputRow = document.getElementById("expense-input-row")
const divExpenseAddBtn = document.getElementById("expense-add+btn")
const expenseAddBtn = document.getElementById("add-btn-expense")
const expenseTotal1 = document.getElementById("expense-total-1")
const expenseTotal2 = document.getElementById("expense-total-2")

var incomes = [];
const incomeList = document.getElementById("income-list")
const incomeInputRow = document.getElementById("income-input-row")
const divIncomeAddBtn = document.getElementById("income-add+btn")
const incomeAddBtn = document.getElementById("add-btn-income")
const incomeTotal1 = document.getElementById("income-total-1")
const incomeTotal2 = document.getElementById("income-total-2")

const incomeAndExpenseTotal = document.getElementById("income-expense-total")

const incomeValueInput = document.getElementById("income-name-value")
const incomeTitleInput = document.getElementById("input-income-title")

const expenseValueInput = document.getElementById("expense-name-value")
const expenseTitleInput = document.getElementById("input-expense-title")

const incomeTotal5 = document.getElementById("income-total-5")
const expenseTotal5 = document.getElementById("expense-total-5")
const incomeAndExpenseTotal5 = document.getElementById("income-expense-total-5")

const m1 = document.getElementById("m1");
const m2 = document.getElementById("m2");

const saveBtn = document.getElementById("save-btn");
const logoutBtn = document.getElementById("logout-btn");

const budgetName = document.getElementById("budget-name");

const budgetListElement = document.getElementById("budget-list");

const budgetSheetDiv = document.getElementById("budget-sheet");
const budgetListContainer = document.getElementById("budget-list-container");
const backToBudgetsBtn = document.getElementById("back-to-budgets-btn");

function renderExpenses() {
    expenseList.innerHTML = ""

    for (let i = 0; i < expenses.length; i++) {
        let expense = expenses[i]

        let li = document.createElement("li")
        li.className = "item input-row item-row"

        let div_2 = document.createElement("div")

        let input_expense_title = document.createElement("input")
        input_expense_title.className = "input-title"
        input_expense_title.type = "text"
        input_expense_title.pattern = "[A-Za-z0-9]+"
        input_expense_title.placeholder = "INCOME"
        input_expense_title.disabled = true
        input_expense_title.value = expense.title

        let div_3 = document.createElement("div")

        let input_expense_value = document.createElement("input")
        input_expense_value.className = "input-value"
        input_expense_value.type = "text"
        input_expense_value.pattern = "[0-9]+"
        input_expense_value.placeholder = "VALUE"
        input_expense_value.disabled = true;
        input_expense_value.value = expense.value.toFixed(2)

        let h1_money_sign = document.createElement("h1")
        h1_money_sign.className = "money-sign"
        h1_money_sign.innerHTML = "£"

        div_2.appendChild(input_expense_title)
        div_3.appendChild(input_expense_value)

        let minus = document.createElement("i")
        minus.className = "fa-solid fa-minus minus"

        li.appendChild(div_2)
        li.appendChild(h1_money_sign)
        li.appendChild(div_3)
        li.appendChild(minus)

        expenseList.appendChild(li)

        div_3.addEventListener("click", (event) => input_expense_value.disabled = false)
        div_2.addEventListener("click", (event) => input_expense_title.disabled = false)
        input_expense_value.addEventListener("change", (event) => { input_expense_value.disabled = true; updateExpense({ index: i, title: input_expense_title.value, value: input_expense_value.value }) })
        input_expense_title.addEventListener("change", (event) => { input_expense_title.disabled = true; updateExpense({ index: i, title: input_expense_title.value, value: input_expense_value.value }) })
        minus.addEventListener("click", (event) => removeExpense(i))
    }

    expenseList.appendChild(expenseInputRow)
    expenseList.appendChild(divExpenseAddBtn)

    expenseList.scrollTop = expenseList.scrollHeight;
}

function updateExpense(data) {
    let { index, title, value } = data
    expenses[index].title = title;
    expenses[index].value = parseFloat(value);

    renderExpenses();
    calculateTotals();
}

function renderIncomes() {
    incomeList.innerHTML = ""

    for (let i = 0; i < incomes.length; i++) {
        let income = incomes[i]

        let li = document.createElement("li")
        li.className = "item input-row item-row"

        let div_2 = document.createElement("div")

        let input_income_title = document.createElement("input")
        input_income_title.className = "input-title"
        input_income_title.type = "text"
        input_income_title.pattern = "[A-Za-z0-9]+"
        input_income_title.placeholder = "INCOME"
        input_income_title.disabled = true
        input_income_title.value = income.title

        let div_3 = document.createElement("div")

        let input_income_value = document.createElement("input")
        input_income_value.className = "input-value"
        input_income_value.type = "text"
        input_income_value.pattern = "[0-9]+"
        input_income_value.placeholder = "VALUE"
        input_income_value.disabled = true
        input_income_value.value = income.value.toFixed(2)

        let h1_money_sign = document.createElement("h1")
        h1_money_sign.className = "money-sign"
        h1_money_sign.innerHTML = "£"

        div_2.appendChild(input_income_title)
        div_3.appendChild(input_income_value)

        let minus = document.createElement("i")
        minus.className = "fa-solid fa-minus minus"

        li.appendChild(div_2)
        li.appendChild(h1_money_sign)
        li.appendChild(div_3)
        li.appendChild(minus)

        incomeList.appendChild(li)

        div_3.addEventListener("click", (event) => input_income_value.disabled = false)
        div_2.addEventListener("click", (event) => input_income_title.disabled = false)
        input_income_value.addEventListener("change", (event) => { input_income_value.disabled = true; updateIncome({ index: i, title: input_income_title.value, value: input_income_value.value }) })
        input_income_title.addEventListener("change", (event) => { input_income_title.disabled = true; updateIncome({ index: i, title: input_income_title.value, value: input_income_value.value }) },)
        minus.addEventListener("click", (event) => removeIncome(i))
    }

    incomeList.appendChild(incomeInputRow)
    incomeList.appendChild(divIncomeAddBtn)

    incomeList.scrollTop = incomeList.scrollHeight;
}

function updateIncome(data) {
    let { index, title, value } = data
    incomes[index].title = title;
    incomes[index].value = parseFloat(value);

    renderIncomes();
    calculateTotals();
}

function addIncome() {
    const incomeNameInput = document.getElementById("input-income-title");

    const incomeName = incomeNameInput.value.trim();
    const incomeValue = parseFloat(incomeValueInput.value);

    if (incomeName !== "" && !isNaN(incomeValue)) {
        const income = {
            title: incomeName,
            value: incomeValue
        };

        incomes.push(income);
        incomeNameInput.value = "";
        incomeValueInput.value = "";

        renderIncomes();
        calculateTotals();
    }
}

function addExpense() {
    const expenseNameInput = document.getElementById("input-expense-title");

    const expenseName = expenseNameInput.value.trim();
    const expenseValue = parseFloat(expenseValueInput.value);

    if (expenseName !== "" && !isNaN(expenseValue)) {
        const expense = {
            title: expenseName,
            value: expenseValue
        };

        expenses.push(expense);
        expenseNameInput.value = "";
        expenseValueInput.value = "";

        renderExpenses();
        calculateTotals();
    }
}

function removeIncome(index) {
    incomes.splice(index, 1);
    renderIncomes();
    calculateTotals();
}

function removeExpense(index) {
    expenses.splice(index, 1);
    renderExpenses();
    calculateTotals();
}

function calculateTotals() {
    const incomeTotal = incomes.reduce((a, b) => a + b.value, 0).toFixed(2)

    incomeTotal1.innerHTML = incomeTotal
    incomeTotal2.innerHTML = incomeTotal

    const expenseTotal = expenses.reduce((a, b) => a + b.value, 0).toFixed(2)

    expenseTotal1.innerHTML = expenseTotal
    expenseTotal2.innerHTML = expenseTotal

    const incomeMinusExpenseTotal = (incomeTotal - expenseTotal).toFixed(2)

    incomeAndExpenseTotal.classList.remove("black")
    incomeAndExpenseTotal5.classList.remove("black")
    m1.classList.remove("black")
    m2.classList.remove("black")

    if (incomeMinusExpenseTotal < 0) {
        incomeAndExpenseTotal.classList.add("black")
        incomeAndExpenseTotal5.classList.add("black")
        m1.classList.add("black")
        m2.classList.add("black")
    }

    incomeAndExpenseTotal.innerHTML = Math.abs(incomeMinusExpenseTotal).toFixed(2)

    incomeTotal5.innerHTML = incomeTotal
    expenseTotal5.innerHTML = expenseTotal

    incomeAndExpenseTotal5.innerHTML = Math.abs(incomeMinusExpenseTotal).toFixed(2)

    const totalSymbol = incomeMinusExpenseTotal < 0 ? "-£" : "£";

    m1.innerHTML = totalSymbol
    m2.innerHTML = totalSymbol
}

function removeNonNumericCharacters(inputString) {
    return inputString.replace(/[^\d.]/g, "");
}

function removeNonNumericCharactersFromInputValue(input) {
    input.value = removeNonNumericCharacters(input.value)
}

function logout() {
    removeFromStorage("budget");
    removeFromStorage("tokie");
    location.href = '../';
}

function storeValueInStorage(key, value) {
    localStorage.setItem(key, value)
}

function removeFromStorage(key) {
    localStorage.removeItem(key)
}

function retrieveFromStorage(key) {
    localStorage.getItem(key)
}

function getBudgetName() {
    return budgetName.value ? budgetName.value : getCurrentDateInDDMMYYYYFormat();

}

function getCurrentDateInDDMMYYYYFormat() {
    const currentDate = new Date();
    const day = String(currentDate.getDate()).padStart(2, '0');
    const month = String(currentDate.getMonth() + 1).padStart(2, '0'); // Months are zero-based
    const year = currentDate.getFullYear();
    return `${day}-${month}-${year}`;
}

function save() {
    const budgetName = getBudgetName();
    expenses = expenses.map((value, index) => { value.position = index; return value });
    incomes = incomes.map((value, index) => { value.position = index; return value });

    const requestBody = {
        budgetName: budgetName,
        expenses: expenses,
        incomes: incomes
    }

    if (!retrieveFromStorage("tokie")) {
        storeValueInStorage("budget", requestBody)
        location.href = "../login"
    }

    console.log("saving ........")
    console.log(requestBody);

    const url = "../ap1/v1/budget/save"

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": retrieveFromStorage("tokie")
        },
        body: JSON.stringify(requestBody),
    })
        .then(response => {
            console.log(response.status)
            if (response.status === 403) {
                removeFromStorage("tokie");
                storeValueInStorage("budget", requestBody)
                location.href = "../login";
            }
            return response.json();
        })
        .then(result => {
            console.log(result)
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function goBackToBudgets() {
    showBudgetList();
    hideBudgetSheet();
}

function createBudgetIcon(budgetInfo) {
    let outerDiv = document.createElement("div");
    let imgDiv = document.createElement("div");
    let img = document.createElement("img")
    img.src = "icons/sheet-1-icon.png"
    let p = document.createElement("p");
    p.innerText = budgetInfo.title;

    outerDiv.addEventListener("click", () => {
        expenses = budgetInfo.expenses;
        incomes = budgetInfo.incomes;
        renderExpenses();
        renderIncomes();
        showBudgetSheet();
        hideBudgetList();
    });

    imgDiv.appendChild(img);
    outerDiv.appendChild(imgDiv);
    outerDiv.appendChild(p);

    return outerDiv;
}

function createAddBudgetIcon() {
    let outerDiv = document.createElement("div");
    let imgDiv = document.createElement("div");
    let img = document.createElement("img")
    img.src = "icons/add-button-icon.png"
    let p = document.createElement("p");
    p.innerText = "Add New";

    imgDiv.appendChild(img);
    outerDiv.appendChild(imgDiv);
    outerDiv.appendChild(p);

    outerDiv.addEventListener("click", () => {
        showBudgetSheet();
        hideBudgetList();
    });

    return outerDiv;
}

function hideBudgetSheet() {
    budgetSheetDiv.style.display = "none";
}

function showBudgetSheet() {
    budgetSheetDiv.style.display = "flex";
}

function hideBudgetList() {
    budgetListContainer.style.display = "none";
}

function showBudgetList() {
    budgetListContainer.style.display = "block";
}

function addBudgetsToScreen() {
    budgetListElement.innerHTML = "";

    budgetListElement.appendChild(createAddBudgetIcon())

    if (retrieveFromStorage("budgets")) {
        let budgets = JSON.parse(retrieveFromStorage("budgets"));
        budgets.forEach(budget => {
            budgetListElement.appendChild(createBudgetIcon(budget));
        })
    }

    for (let i=0; i < 10; i++) {
        budgetListElement.appendChild(createBudgetIcon({title:"id"+i}));
    }
}

function addEventListeners() {
    incomeAddBtn.addEventListener("click", addIncome);
    expenseAddBtn.addEventListener("click", addExpense);

    incomeValueInput.addEventListener("input", (event) => removeNonNumericCharactersFromInputValue(event.target));
    expenseValueInput.addEventListener("input", (event) => removeNonNumericCharactersFromInputValue(event.target));

    incomeValueInput.addEventListener("change", addIncome);
    expenseValueInput.addEventListener("change", addExpense);
    incomeTitleInput.addEventListener("change", addIncome);
    expenseTitleInput.addEventListener("change", addExpense);
    saveBtn.addEventListener("click", save);
    logoutBtn.addEventListener("click", logout);

    backToBudgetsBtn.addEventListener("click", goBackToBudgets);

    document.getElementById("page-title-id").addEventListener("click", (event) => {
        location.href = "../";
    })
}

document.addEventListener("DOMContentLoaded", () => {
    addEventListeners();
    addBudgetsToScreen();
});