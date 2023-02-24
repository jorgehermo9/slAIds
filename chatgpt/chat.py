from dotenv import load_dotenv
import os


load_dotenv()

email = os.getenv("EMAIL")
password = os.getenv("PASSWORD")

from revChatGPT.V1 import Chatbot

from revChatGPT.V1 import Chatbot

chatbot = Chatbot(config={"email": email, "password": password})

prompt = "how many beaches does portugal have?"
response = ""

for data in chatbot.ask(prompt):
    response = data["message"]

print(response)
