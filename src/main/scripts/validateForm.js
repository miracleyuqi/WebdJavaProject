/**
 * 
 */


function validateForm() {
    var firstName = document.getElementById("firstName").value;
    if (firstName == "") {
        alert("Please enter your first name.");
        return false;
    }

    if (errors.length > 0) {
        alert(errors.join("\n"));
        return false;
    }
    return true;
}

