package com.flemis.score.features.app.domain.usecases

import com.flemis.score.features.app.domain.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserUsecase: KoinComponent {
    private val userRepository: UserRepository by inject()
}