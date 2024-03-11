import cv2


def remove_lines(image, colors):
    gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    bin_image = cv2.threshold(gray_image, 0, 255, cv2.THRESH_BINARY_INV + cv2.THRESH_OTSU)[1]

    h_contours = get_contours(bin_image, (25, 1))
    v_contours = get_contours(bin_image, (1, 25))

    for contour in h_contours:
        cv2.drawContours(image, [contour], -1, colors[0][0], 2)

    for contour in v_contours:
        cv2.drawContours(image, [contour], -1, colors[0][0], 2)

    return image


def get_contours(bin_image, initial_kernel):
    kernel = cv2.getStructuringElement(cv2.MORPH_RECT, initial_kernel)

    detected_lines = cv2.morphologyEx(bin_image, cv2.MORPH_OPEN, kernel, iterations=2)

    contours = cv2.findContours(detected_lines, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    contours = contours[0] if len(contours) == 2 else contours[1]

    return contours


img_file = "C:/Users/SSAFY/Documents/bogeun/pjt_2/S10P22D101/AI/tesseract/test.jpg"
img = cv2.imread(img_file)
cv2.imwrite("out.jpg", remove_lines(img, [[0, 0, 0]]))
