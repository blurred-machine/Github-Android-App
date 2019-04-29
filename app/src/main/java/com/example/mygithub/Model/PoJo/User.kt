package com.example.mygithub.Model.PoJo

data class User (var login: String, var avatar_url: String, var html_url: String,
                 var name: String, var company: String, var location: String,
                 var email: String, var bio: String, var id: Int, var followers: Int,
                 var following: Int)