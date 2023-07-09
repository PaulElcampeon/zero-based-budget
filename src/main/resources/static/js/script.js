//document.addEventListener("DOMContentLoaded", function() {
    // Code to be executed when the document is loaded
    console.log("Document loaded!");

    // Expenses data
    var expenses = [];
    const expenseList = document.getElementById("expense-list")
    const expenseInputRow = document.getElementById("expense-input-row")
    const divExpenseAddBtn = document.getElementById("expense-add+btn")
    const expenseAddBtn = document.getElementById("add-btn-expense")
    const expenseTotal1 = document.getElementById("expense-total-1")
    const expenseTotal2 = document.getElementById("expense-total-2")

    // Incomes data
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

    //Hidden totals div
    const incomeTotal5 = document.getElementById("income-total-5")
    const expenseTotal5 = document.getElementById("expense-total-5")
    const incomeAndExpenseTotal5 = document.getElementById("income-expense-total-5")

    const m1 = document.getElementById("m1");
    const m2 = document.getElementById("m2");

    // Function to render the expenses list
    function renderExpenses() {
        expenseList.innerHTML = ""

        for (let i = 0; i < expenses.length; i++) {
            let expense = expenses[i]

            let div_1 = document.createElement("div")
            div_1.className = "item input-row item-row"

            let div_2 = document.createElement("div")

            let input_expense_title = document.createElement("input")
            input_expense_title.className = "input-title"
            input_expense_title.type = "text"
            input_expense_title.pattern = "[A-Za-z0-9]+"
            input_expense_title.placeholder = "INCOME"
            input_expense_title.disabled = true
            input_expense_title.value = expense.name

            let div_3 = document.createElement("div")

            let input_expense_value = document.createElement("input")
            input_expense_value.className = "input-value"
            input_expense_value.type = "text"
            input_expense_value.pattern = "[0-9]+"
            input_expense_value.placeholder = "VALUE"
            input_expense_value.disabled = true;
            input_expense_value.value = expense.cost.toFixed(2)

            let h1_money_sign = document.createElement("h1")
            h1_money_sign.className = "money-sign"
            h1_money_sign.innerHTML = "£"

            div_2.appendChild(input_expense_title)
            div_3.appendChild(input_expense_value)

            let minus = document.createElement("i")
            minus.className = "fa-solid fa-minus minus"

            div_1.appendChild(div_2)
            div_1.appendChild(h1_money_sign)
            div_1.appendChild(div_3)
            div_1.appendChild(minus)

            expenseList.appendChild(div_1)

            div_3.addEventListener("click", (event) => input_expense_value.disabled = false)
            div_2.addEventListener("click", (event) => input_expense_title.disabled = false)
            input_expense_value.addEventListener("change", (event) => {input_expense_value.disabled = true; updateExpense({index: i, title: input_expense_title.value, value:input_expense_value.value})})
            input_expense_title.addEventListener("change", (event) => {input_expense_title.disabled = true; updateExpense({index: i, title: input_expense_title.value, value:input_expense_value.value})})
            minus.addEventListener("click", (event) => removeExpense(i))
        }

        expenseList.appendChild(expenseInputRow)
        expenseList.appendChild(divExpenseAddBtn)

        expenseList.scrollTop = expenseList.scrollHeight;
    }

    function updateExpense(data) {
        let {index, title, value} = data
        expenses[index].name = title;
        expenses[index].cost = parseFloat(value);

        renderExpenses();
        calculateTotals();
    }


    // Function to render the incomes list
    function renderIncomes() {
        incomeList.innerHTML = ""

        for (let i = 0; i < incomes.length; i++) {
            let income = incomes[i]

            let div_1 = document.createElement("div")
            div_1.className = "item input-row item-row"

            let div_2 = document.createElement("div")

            let input_income_title = document.createElement("input")
            input_income_title.className = "input-title"
            input_income_title.type = "text"
            input_income_title.pattern = "[A-Za-z0-9]+"
            input_income_title.placeholder = "INCOME"
            input_income_title.disabled = true
            input_income_title.value = income.name

            let div_3 = document.createElement("div")

            let input_income_value = document.createElement("input")
            input_income_value.className = "input-value"
            input_income_value.type = "text"
            input_income_value.pattern = "[0-9]+"
            input_income_value.placeholder = "VALUE"
            input_income_value.disabled = true
            input_income_value.value = income.cost.toFixed(2)

            let h1_money_sign = document.createElement("h1")
            h1_money_sign.className = "money-sign"
            h1_money_sign.innerHTML = "£"

            div_2.appendChild(input_income_title)
            div_3.appendChild(input_income_value)

            let minus = document.createElement("i")
            minus.className = "fa-solid fa-minus minus"

            div_1.appendChild(div_2)
            div_1.appendChild(h1_money_sign)
            div_1.appendChild(div_3)
            div_1.appendChild(minus)

            incomeList.appendChild(div_1)

            div_3.addEventListener("click", (event) => input_income_value.disabled = false)
            div_2.addEventListener("click", (event) => input_income_title.disabled = false)
            input_income_value.addEventListener("change", (event) => {input_income_value.disabled = true; updateIncome({index: i,title: input_income_title.value, value:input_income_value.value})})
            input_income_title.addEventListener("change", (event) => {input_income_title.disabled = true; updateIncome({index: i,title: input_income_title.value, value:input_income_value.value})},)
            minus.addEventListener("click", (event) => removeIncome(i))
        }

        incomeList.appendChild(incomeInputRow)
        incomeList.appendChild(divIncomeAddBtn)

        incomeList.scrollTop = incomeList.scrollHeight;
    }

    function updateIncome(data) {
        let {index, title, value} = data
        incomes[index].name = title;
        incomes[index].cost = parseFloat(value);

        renderIncomes();
        calculateTotals();
    }

    // Function to add a new income
    function addIncome() {
      const incomeNameInput = document.getElementById("input-income-title");

      const incomeName = incomeNameInput.value.trim();
      const incomeValue = parseFloat(incomeValueInput.value);

      if (incomeName !== "" && !isNaN(incomeValue)) {
        const income = {
          name: incomeName,
          cost: incomeValue
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
                name: expenseName,
                cost: expenseValue
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
        const incomeTotal = incomes.reduce((a, b) => a + b.cost, 0).toFixed(2)

        incomeTotal1.innerHTML = incomeTotal
        incomeTotal2.innerHTML = incomeTotal

        const expenseTotal = expenses.reduce((a, b) => a + b.cost, 0).toFixed(2)

        expenseTotal1.innerHTML = expenseTotal
        expenseTotal2.innerHTML = expenseTotal

        const incomeMinusExpenseTotal =  (incomeTotal - expenseTotal).toFixed(2)

        incomeAndExpenseTotal.innerHTML =  Math.abs(incomeMinusExpenseTotal).toFixed(2)

        incomeTotal5.innerHTML = incomeTotal
        expenseTotal5.innerHTML = expenseTotal

        incomeAndExpenseTotal5.innerHTML = Math.abs(incomeMinusExpenseTotal).toFixed(2)

        const totalSymbol = incomeMinusExpenseTotal<0? "-£" : "£";

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
        localStorage.removeItem("tokie")
    }

    // Add event listener to the add button
    incomeAddBtn.addEventListener("click", addIncome);
    expenseAddBtn.addEventListener("click", addExpense);

    incomeValueInput.addEventListener("input", (event) => removeNonNumericCharactersFromInputValue(event.target));
    expenseValueInput.addEventListener("input", (event) => removeNonNumericCharactersFromInputValue(event.target));

    incomeValueInput.addEventListener("change", addIncome);
    expenseValueInput.addEventListener("change", addExpense);
    incomeTitleInput.addEventListener("change", addIncome);
    expenseTitleInput.addEventListener("change", addExpense);
//});