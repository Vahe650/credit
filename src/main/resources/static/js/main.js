function validateForm() {
    var valid = 1;
    var name = document.getElementById('name');
    var name_validation = document.getElementById("name_validation");


    if (name.value === "") {
        valid = 0;
        name_validation.innerHTML = "Անունը լրացրեք";
        name_validation.style.display = "block";
        name_validation.parentNode.style.backgroundColor = "#FFDFDF";
    } else {
        name_validation.style.display = "none";
        name_validation.parentNode.style.backgroundColor = "transparent";
    }
    if (!valid)
        return false;
}