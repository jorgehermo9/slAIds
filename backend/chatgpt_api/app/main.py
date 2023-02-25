from dotenv import load_dotenv
import os
from fastapi import FastAPI, HTTPException
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


@app.post("/chat")
def conversation(prompt: Prompt):

    try:
        if prompt.conversation_id is None and len(chatbot.get_conversations()) > 0:
            chatbot.reset_chat()

        for data in chatbot.ask(prompt.text, prompt.conversation_id, prompt.parent_id):
            response = data

        text_response = response["message"]
        conversation_id = response["conversation_id"]
        parent_id = response["parent_id"]
        chat_response = PromptResponse(
            response=text_response, conversation_id=conversation_id, parent_id=parent_id
        )
        return chat_response
    except Exception as e:
        print(e)
        raise HTTPException(status_code=500, detail=str(e))
