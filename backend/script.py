import requests
import base64
import io
from PIL import Image
import sys
payload = {
    "prompt": sys.argv[1],
    "steps": 10,
}


response = requests.post(url=f"http://localhost:7860/sdapi/v1/txt2img", json=payload)

r = response.json()

for i in r['images']:
  image = Image.open(io.BytesIO(base64.b64decode(i.split(",", 1)[0])))
  image.show()
