package love.forte.simbot.component.kritor.core

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.subclass
import love.forte.simbot.bot.serializableBotConfigurationPolymorphic
import love.forte.simbot.common.function.ConfigurerFunction
import love.forte.simbot.common.function.invokeWith
import love.forte.simbot.component.Component
import love.forte.simbot.component.ComponentConfigureContext
import love.forte.simbot.component.ComponentFactory
import love.forte.simbot.component.kritor.core.bot.SerializableKritorBotConfiguration
import love.forte.simbot.component.kritor.core.message.includeAllComponentMessageElementImpls
import love.forte.simbot.message.messageElementPolymorphic


/**
 * 一个 `Kritor` 组件标识。
 *
 * @author ForteScarlet
 */
public class KritorComponent : Component {
    override val id: String
        get() = ID_VALUE

    /**
     * 值同 [Factory.SerializersModule], 组件范围内额外提供的可序列化信息。
     */
    override val serializersModule: SerializersModule
        get() = SerializersModule

    override fun toString(): String =
        "KritorComponent(id=$ID_VALUE)"

    public companion object Factory : ComponentFactory<KritorComponent, KritorComponentConfiguration> {
        private val INSTANCE = KritorComponent()

        /**
         * `"simbot.kritor"`, [KritorComponent] 的组件标识ID值。
         */
        override val key: ComponentFactory.Key = object : ComponentFactory.Key {}
        public const val ID_VALUE: String = "simbot.kritor"

        @JvmField
        public val SerializersModule: SerializersModule = SerializersModule {
            serializableBotConfigurationPolymorphic {
                subclass(SerializableKritorBotConfiguration.serializer())
            }

            messageElementPolymorphic {
                // 所有 KritorMessageElement 的序列化信息
                includeAllComponentMessageElementImpls()
            }
        }

        override fun create(
            context: ComponentConfigureContext,
            configurer: ConfigurerFunction<KritorComponentConfiguration>
        ): KritorComponent {
            configurer.invokeWith(KritorComponentConfiguration())
            return INSTANCE
        }
    }
}


public class KritorComponentConfiguration
