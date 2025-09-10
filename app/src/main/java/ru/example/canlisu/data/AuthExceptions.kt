package ru.example.canlisu.data

class UserNotFoundException : Exception("user_not_found")
class InvalidPasswordException : Exception("invalid_password")
class EmailAlreadyExistsException : Exception("email_exists")
class PhoneAlreadyExistsException : Exception("phone_exists")
