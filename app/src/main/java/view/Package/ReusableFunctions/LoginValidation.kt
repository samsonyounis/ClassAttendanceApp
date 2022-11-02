package view.Package.ReusableFunctions



fun loginValidation(userName:String, password:String):String {
    if (userName.isBlank()){
        return "username is blank"
    }
    else if (password.length < 8) {
        return "password too short"
    }
    else return ""
}