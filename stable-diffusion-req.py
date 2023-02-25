import requests
import base64
import io
from PIL import Image

payload = {
    "prompt": "maltese puppy",
    "batch_size": 1,
    "n_iter": 1,
    "steps": 5,
    "width": 512,
    "height": 512,
}


response = requests.post(url=f"http://172.20.10.6:7860/sdapi/v1/txt2img", json=payload)

r = response.json()

for i in r["images"]:
    image = Image.open(io.BytesIO(base64.b64decode(i.split(",", 1)[0])))
    image.show()
