# check_image_quality.py
from flask import Flask, request, jsonify
import cv2
import numpy as np
from io import BytesIO
from PIL import Image

app = Flask(__name__)

def is_white_or_black(img):
    avg_pixel = np.mean(img)
    if avg_pixel > 250:
        return "white"
    elif avg_pixel < 5:
        return "black"
    return "normal"

def is_blurry(img, threshold=100):
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    lap_var = cv2.Laplacian(gray, cv2.CV_64F).var()
    return lap_var < threshold  # mờ nếu dưới ngưỡng

@app.route('/check', methods=['POST'])
def check_image():
    from io import BytesIO
    import numpy as np
    import cv2
    from PIL import Image

    try:
        raw_data = request.data
        img = Image.open(BytesIO(raw_data))
        img_cv = cv2.cvtColor(np.array(img), cv2.COLOR_RGB2BGR)

        blurry = bool(is_blurry(img_cv))
        color_status = is_white_or_black(img_cv)

        return jsonify({
            "blurry": blurry,
            "color": color_status
        })

    except Exception as e:
        print("Error processing image:", e)
        return jsonify({"error": str(e)}), 400

if __name__ == '__main__':
    app.run(port=5001)
