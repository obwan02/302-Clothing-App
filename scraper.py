from selenium import webdriver
from selenium.webdriver.common.by import By
import requests
import time
import random
import json


def title_to_json_url(title: str):
    id = title.lower().strip().replace("- ", "").replace(" ", "-").replace("/", "-")
    return f"https://www.iloveugly.co.nz/products/{id}.js"


if __name__ == "__main__":
    browser = webdriver.Chrome()

    # Mens
    browser.get("https://www.iloveugly.co.nz/collections/all-womenswear")
    SCROLL_PAUSE_TIME = 1.0

    # Get scroll height
    last_height = browser.execute_script("return document.body.scrollHeight")

    for _ in range(100):
        browser.execute_script("window.scrollTo(0, document.body.scrollHeight);")
        time.sleep(SCROLL_PAUSE_TIME)

        new_height = browser.execute_script("return document.body.scrollHeight")
        if new_height == last_height:
            break
        last_height = new_height
    else:
        print("Something went wrong (infinite scroll)")

    title_elements = browser.find_elements(By.CLASS_NAME, "ProductItem__Title")
    urls = [title_to_json_url(item.text) for item in title_elements]

    items = []

    for url in urls:
        try:
            info = requests.get(url).json()

            keep = {
                "title": info["title"],
                "description": info["description"],
                "category": info["type"],
                "price": int(info["price"]) / 100,
                "images": info["images"],
                "size": random.choice(["XS", "S", "M", "L", "XL"]),
                "gender": "female",
            }
            items.append(keep)
            print(f"{info['title']} - {keep['size']}")
        except Exception as e:
            print(f"Failed {url}: {e}")

    with open("female.json", "w") as file:
        json.dump(items, file)
