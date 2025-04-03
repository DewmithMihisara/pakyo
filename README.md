# PakYO - Excel to SQL Generator

**PakYO** is a **JavaFX-based** application that converts Excel files (`.xlsx`) into SQL `INSERT` queries. It allows users to input a **table creation query**, automatically detects column data types, maps them to Excel headers, and generates SQL scripts that can be saved to a chosen location.

## Features
- **Create Table Query Input** – Define the database table schema directly in the UI.
- **Excel File Upload** – Select an `.xlsx` file with data.
- **Auto Column Matching** – Map Excel headers to table columns.
- **Generate INSERT Queries** – Automatically generate SQL insert statements.
- **Custom Save Location & Filename** – Choose where to save the generated SQL file.

---

## Architecture (MVC Pattern)
- **Model (M)** → Handles data processing (Excel parsing, SQL query generation).
- **View (V)** → JavaFX UI components (FXML layouts, input fields, file selection).
- **Controller (C)** → Manages UI logic, user actions, and connects Model & View.

---

## Tech Stack
- **Java 17**
- **JavaFX (FXML)**
- **Apache POI** (Excel file processing)

---

## How to Use

### **Input Table Schema**
- Enter the **SQL CREATE TABLE** statement in the UI.
- The application extracts column names and data types.

### **Upload Excel File**
- Select an `.xlsx` file with data.
- The application automatically **matches headers** with table columns.

### **Choose Save Location & Filename**
- Select the folder where you want to **save the SQL file**.
- Enter a **custom filename**.

### **Generate SQL Script**
- Click **"Generate SQL"**.
- The application creates **INSERT statements**.
- SQL script is saved to the chosen location.

---

## Installation & Setup
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

## Future Enhancements
- **Support for multiple databases (MySQL, PostgreSQL, SQL Server, SQLite)**
- **Batch SQL execution directly in DB**
- **UI improvements & dark mode**

---

## Contributing
Pull requests are welcome! Feel free to improve UI, optimize query generation, or add new features.

---
## License

This project is licensed under the [MIT License](LICENSE).

---
## Contact

Feel free to reach out to me for any inquiries, collaboration opportunities, or just to say hello! You can contact me via:

* Email : mihisaralokuhewage@gmail.com
* LinkedIn : [https://linkedin.com/in/dewmith-mihisara-67861a202](https://linkedin.com/in/dewmith-mihisara-67861a202)
* Twitter : [https://twitter.com/Zyne_Galata](https://twitter.com/Zyne_Galata)

---
***
</h5>
<div align="center">
  
![repo size](https://img.shields.io/github/repo-size/DewmithMihisara/pakyo?label=Repo%20Size&style=for-the-badge&labelColor=black&color=20bf6b)
![GitHub stars](https://img.shields.io/github/stars/DewmithMihisara/pakyo?&labelColor=black&color=f7b731&style=for-the-badge)
![GitHub LastCommit](https://img.shields.io/github/last-commit/DewmithMihisara/pakyo?logo=github&labelColor=black&color=d1d8e0&style=for-the-badge)

</div>

