package com.easymove.easymove.ticket

import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val ticketModule = module {
    fragment { TicketFragment() }
}