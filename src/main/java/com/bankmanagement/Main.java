/*
* BANK ACCOUNT MANAGEMENT SYSTEM
*
* Team members:
* Karla Dinorah Valdivieso Ramos
* Wendolynee Macharro Armadillo
* José Alfredo Peña Mejía
* Jared Alexander Trujillo Ortiz
*
* Date: November 16th, 2023
* Description of the project:
*
* The system is going to consist of two main parts, the administrator and the user.
The administrator as the manager of the company will have the control of the budget,
you can deposit or withdraw the money from the bank account, also you can register
members and make modifications of them, moreover you can have the possibility to
see the different transactions that are being made, when, where and who.
On the other side the user can only withdraw the money from the bank account, but
to do this the user has to be in the system already registered by the administrator,
otherwise there won’t be any other way to access the system.
*
* */

package com.bankmanagement;

import com.classes.Views;

public class Main {
    public static void main(String[] args) {

        Views displayViews = new Views();
        displayViews.displayLandingMenu();

    }
}