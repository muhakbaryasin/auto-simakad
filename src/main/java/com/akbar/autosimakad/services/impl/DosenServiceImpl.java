package com.akbar.autosimakad.services.impl;

import com.akbar.autosimakad.MataKuliahResponse;
import com.akbar.autosimakad.SimakResponse;
import com.akbar.autosimakad.services.DosenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.springframework.stereotype.Service;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileReader;
import java.lang.Thread;
import java.nio.file.Path;


@Service
public class DosenServiceImpl implements DosenService {
    private static final String URL_DOSEN = "https://dosen.umkendari.ac.id/";
    private WebDriver wd = null;

    private void init() {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver_win32/geckodriver.exe");
        var ffOpt = new FirefoxOptions();
        ffOpt.setBinary(Path.of("C:\\Program Files\\Mozilla Firefox\\firefox.exe"));
        wd = new FirefoxDriver(ffOpt);
    }

    @Override
    public void inputNilai() throws InterruptedException, JsonProcessingException {
        this.wd.get("view-source:" + "https://dosen.umkendari.ac.id/dosen/Entri/getMataKuliah?tahun=20221");
        var json_ = this.wd.findElement(By.tagName("pre")).getText();
        SimakResponse mkResponse = null;

        try {
            mkResponse = new ObjectMapper().readValue(json_, SimakResponse.class);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (mkResponse != null) {
            for (var kuliah:
                 mkResponse.data) {
                if (kuliah.Course_Name.contains("Algoritma")) {
                    this.wd.get(String.format("https://dosen.umkendari.ac.id/dosen/Input/%s/%s/%s/%s/%s/%s", kuliah.Class_Id, kuliah.Class_Prog_Id, kuliah.Course_Id, kuliah.Term_Year_Id, kuliah.Department_Id, kuliah.Course_Type_Id));
                    Thread.sleep(10000);
                }

            }
        }

        this.wd.get("https://dosen.umkendari.ac.id");
        /*
        this.wd.get("https://dosen.umkendari.ac.id/dosen/Nilai");
        var tahunOptEl = this.wd.findElement(By.xpath("//*[@id=\"grid\"]/div[1]/div/span"));
        tahunOptEl.click();
        var options = this.wd.findElement(By.id("category_listbox")).findElements(By.tagName("li"));

        for (var each: options) {
            if (each.getText().contains("20221")) {
                each.click();
                break;
            }
        }

        var entries = this.wd.findElement(By.xpath("//*[@id=\"grid\"]/div[3]/table"));
        var rows = entries.findElements(By.tagName("tr"));

        for (var row:
             rows) {
            var thisIsIt = false;

            for (var col:
                 row.findElements(By.tagName("td"))) {
                if (col.getText().contains("Algoritma")) {
                    thisIsIt = true;
                } else if (col.getText().contains())
            }
        }*/

        Thread.sleep(10000);

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader("src/main/resources/files/example.csv"));

            String[] nextLine;
            //read one line at a time
            while ((nextLine = reader.readNext()) != null) {
                for (String token: nextLine) {
                    System.out.println(token);
                }
                System.out.print("\n");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void login() throws InterruptedException {
        init();
        // Calling the Home Page By Using Get() Method
        this.wd.get(URL_DOSEN);

        var emailEl = this.wd.findElement(By.id("email"));
        emailEl.sendKeys("");

        var passwordEl = this.wd.findElement(By.id("password"));
        passwordEl.sendKeys("");

        var buttonLoginEl = this.wd.findElement(By.xpath("/html/body/div[1]/section/div/div[2]/div/form/div[4]/div/button"));
        buttonLoginEl.click();

        // Delaying The Output
        Thread.sleep(3000);
    }

    @Override
    public void logout() throws InterruptedException {
        if(this.wd == null)
            return;

        JavascriptExecutor js = (JavascriptExecutor) this.wd;
        js.executeScript("document.getElementById('logout-form').submit();");
        Thread.sleep(3000);

        // Closing The Opened Window
        this.wd.quit();

        // Delaying The Output
        this.wd = null;
    }
}
