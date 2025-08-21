package com.sergeyfierce.testapp.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object ContactsList : Screen("contacts_list/{category}") {
        fun createRoute(category: String) = "contacts_list/$category"
    }
    data object ContactAdd : Screen("contact_add?category={category}") {
        fun createRoute(category: String? = null): String {
            return if (category != null) "contact_add?category=$category" else "contact_add"
        }
    }
    data object ContactDetails : Screen("contact_details/{id}") {
        fun createRoute(id: Int) = "contact_details/$id"
    }
    data object NotesList : Screen("notes_list")
    data object NoteAdd : Screen("note_add")
    data object NoteDetails : Screen("note_details/{id}") {
        fun createRoute(id: Int) = "note_details/$id"
    }
    data object Settings : Screen("settings")
}
