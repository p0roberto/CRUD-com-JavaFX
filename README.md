# CRUD JavaFX para Cachorros

Aplicação JavaFX que realiza operações CRUD (Create, Read, Update, Delete) em um banco de dados SQLite para gerenciar informações de cachorros e seus respectivos donos.

---

## ✅ Requisitos

- Java JDK 11 ou superior (recomendado)
- JavaFX SDK 17
- ORMLite (bibliotecas já incluídas na pasta `lib/`)

> 💡 Este projeto não utiliza Maven ou Gradle, portanto é necessário compilar e executar via terminal com os caminhos configurados corretamente.

---

## 📦 Preparação

1. **Baixe e extraia** o JavaFX SDK 17.
2. Coloque o conteúdo extraído em uma pasta de fácil acesso, como:

   ```
   ~/Downloads/openjfx-17.0.15_linux-x64_bin-sdk/javafx-sdk-17.0.15/
   ```

3. Certifique-se de que o diretório `lib/` dentro do SDK contém arquivos `.jar`.

---

## 🛠️ Compilar o projeto

No terminal, estando na raiz do projeto (`javafx-crud`), execute:

```bash
javac \
--module-path ~/Downloads/openjfx-17.0.15_linux-x64_bin-sdk/javafx-sdk-17.0.15/lib \
--add-modules javafx.controls,javafx.fxml \
-cp ".:lib/*" \
-d out/production/javafx-crud \
src/model/*.java src/controller/*.java src/view/*.java
```

> 🔁 Altere o caminho do `--module-path` conforme o local onde você extraiu o JavaFX.

---

## ▶️ Executar o projeto

Após a compilação bem-sucedida, execute:

```bash
java \
--module-path ~/Downloads/openjfx-17.0.15_linux-x64_bin-sdk/javafx-sdk-17.0.15/lib \
--add-modules javafx.controls,javafx.fxml \
-cp "out/production/javafx-crud:lib/*" \
view.AppView
```

---

## 📋 Funcionalidades

- ✅ Adicionar novos cachorros e donos com persistência no banco de dados
- ✅ Visualizar todos os cachorros e donos cadastrados mesmo após fechar e reabrir o programa
- ✅ Atualizar informações de cachorros e donos existentes
- ✅ Remover cachorros e donos do sistema com atualização automática da interface
- ✅ Contador de cachorros atualizado em tempo real ao adicionar ou remover registros
- ✅ Relacionar cada cachorro ao seu respectivo dono com visualização clara na tabela
- ✅ Validação de entrada para os campos de idade e peso dos cachorros


