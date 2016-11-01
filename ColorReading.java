public void getSpecs() {
        //When implementing with camera, change field i to get the image taken from the camera,
        //so it's no longer pre loaded in with Android Studio
        ImageView i = new ImageView(this);
        i.setImageBitmap(b);
        int w = i.getWidth();
        int h = i.getHeight();
        Bitmap bitmap = ((BitmapDrawable) i.getDrawable()).getBitmap();
        int pixel = bitmap.getPixel(w, h);
        red = Color.red(pixel);
        blue = Color.blue(pixel);
        green = Color.green(pixel);

    }