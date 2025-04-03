# PakYO - Excel to SQL Generator

**PakYO** is a **JavaFX-based** application that converts Excel files (`.xlsx`) into SQL `INSERT` queries. It allows users to input a **table creation query**, automatically detects column data types, maps them to Excel headers, and generates SQL scripts that can be saved to a chosen location.

## 📌 Features
- **Create Table Query Input** – Define the database table schema directly in the UI.
- **Excel File Upload** – Select an `.xlsx` file with data.
- **Auto Column Matching** – Map Excel headers to table columns.
- **Generate INSERT Queries** – Automatically generate SQL insert statements.
- **Custom Save Location & Filename** – Choose where to save the generated SQL file.

---

## 🏗 Architecture (MVC Pattern)
- **Model (M)** → Handles data processing (Excel parsing, SQL query generation).
- **View (V)** → JavaFX UI components (FXML layouts, input fields, file selection).
- **Controller (C)** → Manages UI logic, user actions, and connects Model & View.

---

## 🛠 Tech Stack
- **Java 17+/21/22**
- **JavaFX (FXML)**
- **Apache POI** (Excel file processing)
- **JDBC** (for database schema reference)

---

## 🚀 How to Use

### **1️⃣ Input Table Schema**
- Enter the **SQL CREATE TABLE** statement in the UI.
- The application extracts column names and data types.

### **2️⃣ Upload Excel File**
- Select an `.xlsx` file with data.
- The application automatically **matches headers** with table columns.

### **3️⃣ Choose Save Location & Filename**
- Select the folder where you want to **save the SQL file**.
- Enter a **custom filename**.

### **4️⃣ Generate SQL Script**
- Click **"Generate SQL"**.
- The application creates **INSERT statements**.
- SQL script is saved to the chosen location.

---

## 📦 Installation & Setup
1. **Clone the repository**
   ```sh
   git clone https://github.com/yourusername/PakYO.git
   cd PakYO
   ```
2. **Run the application**
   ```sh
   mvn clean javafx:run
   ```

---

## 📌 Future Enhancements
- **Support for multiple databases (MySQL, PostgreSQL, SQL Server, SQLite)**
- **Batch SQL execution directly in DB**
- **UI improvements & dark mode**

---

## 🤝 Contributing
Pull requests are welcome! Feel free to improve UI, optimize query generation, or add new features.

---
