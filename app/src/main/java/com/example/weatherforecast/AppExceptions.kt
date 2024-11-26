package com.example.weatherforecast

import java.io.IOException

open class AppExceptions (message: String?) : IOException(message)

class LoadDataException(message: String?) : AppExceptions(message)