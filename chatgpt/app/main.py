from dotenv import load_dotenv
import os
from fastapi import FastAPI
from revChatGPT.V1 import Chatbot

from pydantic import BaseModel
from typing import Optional

load_dotenv()


class Prompt(BaseModel):
    text: str
    conversation_id: Optional[str]
    parent_id: Optional[str]


class PromptResponse(BaseModel):
    response: str
    conversation_id: str
    parent_id: str


email = os.getenv("EMAIL")
password = os.getenv("PASSWORD")

app = FastAPI()

chatbot = Chatbot(config={"email": email, "password": password})


@app.post("/generate")
def read_root(prompt: Prompt):
    for data in chatbot.ask(prompt.text, prompt.conversation_id, prompt.parent_id):
        response = data

    text_response = response["message"]
    conversation_id = response["conversation_id"]
    parent_id = response["parent_id"]

    return {
        "response": text_response,
        "conversation_id": conversation_id,
        "parent_id": parent_id,
    }
