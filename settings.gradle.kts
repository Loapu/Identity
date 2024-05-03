rootProject.name = "template"

include("api", "common", "paper")
rootProject.children.forEach { it.projectDir = file("modules/${it.name}") }