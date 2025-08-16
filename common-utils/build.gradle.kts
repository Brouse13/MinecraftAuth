plugins {
    id("java")
}

group = property("group") as String
version = property("version") as String

tasks.test {
    useJUnitPlatform()
}