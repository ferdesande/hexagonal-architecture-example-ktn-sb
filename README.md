# hexagonal-architecture-example-ktn-sb
An example or Hexagonal Architecture with Kotlin and Spring Boot

## Documentation

### Detekt
**Detekt** is used as linter in the project. Don't forget to enable it in the configuration of your project.

#### Detekt plugin installing and configuring in IntelliJ
1. Open IntelliJ Settings (``⌘ + , || CRL + ,``). Go to ``Plugins``. Then search for "Detekt" and install it.
2. When Detekt is installed open IntelliJ Settings and go to ``Tools >> Detekt``
3. Check
    - Enable background analysis
    - Enable Rules:
        - Build rules upon the default configuration
        -  Enable formatting (ktlint) rules
4. In Configuration Files add ``detekt.yml``
5. It's not mandatory but highly recommended to enable in Plugin options the **Treat detekt findings as errors**
   checkbox.

Be careful with the Detekt plugin version. The plugin version must be compatible with the Kotlin version in use. For
more info see the [Official Web Site](https://detekt.dev/docs/introduction/compatibility/).
