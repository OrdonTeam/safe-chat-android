package com.safechat

import android.app.Application
import com.safechat.conversation.ConversationActivity
import com.safechat.conversation.ConversationControllerImpl
import com.safechat.conversation.select.SelectConversationActivity
import com.safechat.conversation.select.SelectConversationControllerImpl
import com.safechat.conversation.symmetrickey.ExchangeSymmetricKeyActivity
import com.safechat.conversation.symmetrickey.ExchangeSymmetricKeyControllerImpl
import com.safechat.conversation.symmetrickey.post.PostSymmetricKeyControllerImpl
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyControllerImpl
import com.safechat.encryption.ConversationCipherImpl
import com.safechat.encryption.KeyGeneratorImpl
import com.safechat.encryption.SymmetricKeyCipher
import com.safechat.firebase.conversation.ConversationServiceImpl
import com.safechat.firebase.exchange.ExchangeServiceImpl
import com.safechat.firebase.register.RegisterServiceImpl
import com.safechat.firebase.users.UsersServiceImpl
import com.safechat.register.RegisterControllerImpl
import com.safechat.registration_view.RegisterActivity
import com.safechat.repository.RepositoryImpl
import com.safechat.suid.generator.SuidCalculatorImpl
import com.safechat.user.profile.UserProfileActivity
import com.safechat.user.profile.UserProfileControllerImpl

class SecureMessengerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        RegisterActivity.apply {
            registerControllerProvider = {
                RegisterControllerImpl(it, RepositoryImpl(it), KeyGeneratorImpl(), RegisterServiceImpl())
            }
            openSelectConversation = {
                SelectConversationActivity.start(it)
            }
        }
        SelectConversationActivity.apply {
            selectConversationControllerProvider = {
                SelectConversationControllerImpl(UsersServiceImpl(), it, RepositoryImpl(it))
            }
            onPublicKeySelect = ExchangeSymmetricKeyActivity.start
            onMenuInfoSelect = { UserProfileActivity.start(it) }
        }
        ExchangeSymmetricKeyActivity.apply {
            exchangeSymmetricKeyControllerProvider = {
                val exchangeServiceImpl = ExchangeServiceImpl()
                val retrieveSymmetricKeyControllerImpl = RetrieveSymmetricKeyControllerImpl(exchangeServiceImpl, RepositoryImpl(it), SymmetricKeyCipher())
                val postSymmetricKeyControllerImpl = PostSymmetricKeyControllerImpl(SymmetricKeyCipher(), RepositoryImpl(it), exchangeServiceImpl)
                ExchangeSymmetricKeyControllerImpl(it, RepositoryImpl(it), retrieveSymmetricKeyControllerImpl, postSymmetricKeyControllerImpl)
            }
            onKeyExchange = ConversationActivity.start
        }
        ConversationActivity.apply {
            conversationControllerProvider = {
                val service = ConversationServiceImpl()
                val repository = RepositoryImpl(it)
                val cipher = ConversationCipherImpl()
                ConversationControllerImpl(service, repository, cipher, it)
            }
        }
        UserProfileActivity.apply {
            userProfileControllerProvider = {
                val service = UsersServiceImpl()
                val repository = RepositoryImpl(it)
                val suidCalculator = SuidCalculatorImpl()
                UserProfileControllerImpl(service, repository, suidCalculator, it)
            }
        }
    }
}