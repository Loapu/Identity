plugins {
    id("loapu.plugin-conventions")
    alias(libs.plugins.paperweight.userdev)
}
dependencies {
    implementation(paperweight.paperDevBundle(libs.paper.get().version))
    implementation(libs.commandApi.bukkit)
}
paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION
tasks {
    shadowJar {
        val libsPackage = project.group.toString() + ".lib"
        val dependencyList = listOf(
            libs.commandApi.bukkit.get()
        )
        dependencies {
            dependencyList.forEach {
                include(dependency(it))
            }
        }
        dependencyList.forEach {
            relocate(it.group, libsPackage)
        }
    }
}