package com.automation;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TutorialsNinjaTest {
private Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        // Headless mode enabled for standard CI execution
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        BrowserContext context = browser.newContext();
        page = context.newPage();
    }

    @Test
    public void testLoginSearchAddToCartAndLogout() {
        // 1. Navigate to the target website
        page.navigate("https://tutorialsninja.com/demo/");

        // 2. Click My Account -> Login
        page.locator("//span[text()='My Account']").click();
        page.locator("//a[text()='Login']").click();

        // Fill credentials & submit
        page.fill("#input-email", "testninja100@gmail.com");
        page.fill("#input-password", "pitam@100");
        page.click("input[value='Login']");

        // 3. Search for "laptop"
        page.fill("input[name='search']", "MacBook");
        page.click("button.btn-default");

        // 4. Add the first available laptop item to cart
        Locator addToCartBtn = page.locator("button:has-text('Add to Cart')").first();
        addToCartBtn.click();

        // // Validate success message display
        // Locator successAlert = page.locator(".alert-success");
        // Assert.assertTrue(successAlert.isVisible(), "Success: You have added MacBook to your shopping cart!");

        // 5. Logout
        page.locator("//span[text()='My Account']").click();
        page.locator("//a[text()='Logout']").click();

        // Validate logout header
        Locator logoutHeader = page.locator("#content h1");
        Assert.assertEquals(logoutHeader.textContent().trim(), "Account Logout");
    }

    @AfterClass
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
