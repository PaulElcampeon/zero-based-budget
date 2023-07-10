document.getElementById("create-btn-id").addEventListener("click", (event) => {
    location.href = "../create";
})

document.getElementById("page-title-id").addEventListener("click", (event) => {
    location.href = "../";
})

const what_info = document.getElementById("what-info");
const benefits_info = document.getElementById("benefits-info");
const how_info = document.getElementById("how-info");


document.getElementById("what-toggle").addEventListener("click", (event) => {
    const height = what_info.style.height;

    if (height == "" || height == "0px") {
        openAllCards();
//         what_info.style.height = what_info.scrollHeight + "px";
    } else {
         what_info.style.height = 0 + "px";
    }
})

document.getElementById("benefits-toggle").addEventListener("click", (event) => {
    const height = benefits_info.style.height;

    if (height == "" || height == "0px") {
        openAllCards();
//         benefits_info.style.height = benefits_info.scrollHeight + "px";
    } else {
         benefits_info.style.height = 0 + "px";
    }
})

document.getElementById("how-toggle").addEventListener("click", (event) => {
    const height = how_info.style.height;

    if (height == "" || height == "0px") {
        openAllCards();
         how_info.style.height = how_info.scrollHeight + "px";
    } else {
         how_info.style.height = 0 + "px";
    }
})

function openAllCards() {
    what_info.style.height = what_info.scrollHeight + "px";
    benefits_info.style.height = benefits_info.scrollHeight + "px";
    how_info.style.height = how_info.scrollHeight + "px";
}