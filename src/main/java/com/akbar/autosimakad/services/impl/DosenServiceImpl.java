package com.akbar.autosimakad.services.impl;

import com.akbar.autosimakad.services.DosenService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.springframework.stereotype.Service;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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
    public void inputNilai() {

    }

    @Override
    public void login() throws InterruptedException {
        init();
        // Calling the Home Page By Using Get() Method
        this.wd.get(URL_DOSEN);
        var emailEl = this.wd.findElement(By.id("email"));
        var passwordEl = this.wd.findElement(By.id("password"));
        emailEl.sendKeys("");
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
