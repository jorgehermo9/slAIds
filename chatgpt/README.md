# Install dependencies

```console
pip install -r requirements.txt
```

# Running

```console
uvicorn app.main:app
```

# Endpoint

POST /chat

Body parameters examples:

- {"text": "Hello, how are you?"}
- {"text": "I'm fine, and you?", conversation_id: "1234", parent_id: "1234-1"}

If a conversation should be followed, conversation id and parent id should be provided in
the request body.
