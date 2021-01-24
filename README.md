## Introduction
A sleep tracker app where one can note the start,end,duration of one's sleep,rate it on a scale of 5 and also share the whole data with peers.

## How to use it
Open the app,press the "Start" button while going to sleep, and press "Stop" after waking up (the app works even if its not running in the backround).Rate the sleep and have the sleep info displayed to you along with any previous info you might have saved earlier.

## Tech stack 

*Kotlin based*

*JetPack features*
    
* LiveData - notify domain layer data to views.
* Lifecycle - dispose of observing data when lifecycle state changes.
* ViewModel - UI related data holder, lifecycle aware.

*Architecture*
* MVVM Architecture
