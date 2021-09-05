package ru.frogogo.whitelabel.extension

import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeCallback
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module

private val modules = hashMapOf<String, Module>()

fun Scope.loadBindingModule(declaration: ModuleDeclaration) {
  val module = modules.getOrPut(id) {
    module(moduleDeclaration = declaration)
  }
  loadKoinModules(module)
}

fun Scope.unloadBindingModuleOnClose() {
  registerCallback(
    object : ScopeCallback {
      override fun onScopeClose(scope: Scope) {
        modules.remove(scope.id)?.let(::unloadKoinModules)
      }
    },
  )
}

fun Scope.scopedQualifier(name: String): Qualifier =
  named("$id-$name")
