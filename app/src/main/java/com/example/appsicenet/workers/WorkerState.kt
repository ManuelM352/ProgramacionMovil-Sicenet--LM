package com.example.appsicenet.workers

enum class WorkerState {
    ENQUEUED,
    RUNNING,
    SUCCESS,
    FAILED,
    UNKNOWN
}