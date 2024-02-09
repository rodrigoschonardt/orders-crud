package com.rodrigoschonardt.orderservice.support

import org.jetbrains.exposed.sql.IColumnType
import org.jetbrains.exposed.sql.UUIDColumnType
import org.springframework.aot.hint.ExecutableMode
import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.context.annotation.ImportRuntimeHints
import org.springframework.stereotype.Component
import java.util.*

@Component
@ImportRuntimeHints( HintsRegister.Register::class )
class HintsRegister {
    object Register : RuntimeHintsRegistrar {
        override fun registerHints( hints : RuntimeHints, classLoader : ClassLoader? ) {
            hints.reflection().registerMethod( Collections::class.java.getMethod( "emptyList" ),
                ExecutableMode.INVOKE )
            hints.reflection().registerType( UUIDColumnType::class.java, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                MemberCategory.PUBLIC_FIELDS, MemberCategory.INVOKE_PUBLIC_METHODS, MemberCategory.PUBLIC_FIELDS, MemberCategory.DECLARED_FIELDS, MemberCategory.INTROSPECT_PUBLIC_METHODS )
            hints.reflection().registerType( IColumnType::class.java, MemberCategory.PUBLIC_FIELDS, MemberCategory.INVOKE_PUBLIC_METHODS,
                MemberCategory.PUBLIC_FIELDS, MemberCategory.DECLARED_FIELDS, MemberCategory.INTROSPECT_PUBLIC_METHODS )
        }
    }

}