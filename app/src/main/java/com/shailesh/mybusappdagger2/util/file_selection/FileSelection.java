package com.shailesh.mybusappdagger2.util.file_selection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shailesh.mybusappdagger2.R;
import com.shailesh.mybusappdagger2.util.camera.CameraUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

public class FileSelection {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String captureImage(Activity activity, Integer CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
        String fileStoragePath = new String();

        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File file = CameraUtils.getOutputMediaFile(1);

            if (file != null) {
                fileStoragePath = file.getAbsolutePath();
            }

            Uri fileUri = CameraUtils.getOutputMediaFileUri(activity.getApplicationContext(), file);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            // start the image capture Intent
            activity.startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return fileStoragePath;
    }

    public static void selectImageFromGallery(Activity activity, Integer RESULT_LOAD_IMAGE_Gallery) {
        try {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity.startActivityForResult(i, RESULT_LOAD_IMAGE_Gallery);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void showPermissionsAlert(final Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.permission_required))
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(context);
                    }
                })
                .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void selectVideo(Activity activity, Integer RESULT_LOAD_VIDEO_Gallery) {
        try {
            Intent intent;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            } else {
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI);
            }
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra("return-data", true);
            activity.startActivityForResult(intent, RESULT_LOAD_VIDEO_Gallery);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void selecteDocument(Activity activity, Integer RESULT_LOAD_DOCUMENT) {
        try {
            String[] mimeTypes =
                    {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                            "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                            "text/plain",
                            "application/pdf"};

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                if (mimeTypes.length > 0) {
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }
            } else {
                String mimeTypesStr = "";
                for (String mimeType : mimeTypes) {
                    mimeTypesStr += mimeType + "|";
                }
                intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
            }

            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra("return-data", true);
            activity.startActivityForResult(intent, RESULT_LOAD_DOCUMENT);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // Converting Bitmap image to Base64.encode String type
    public static String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    // Converting File to Base64.encode String type using Method
    public static String getBase64tringFile(Context context, File f)
    {
        String imageEncoded = new String();
        try
        {


            String compressedImg = compressImage(context, f.getPath());
            Bitmap myBitmap = BitmapFactory.decodeFile(compressedImg);
            int nh = (int) (myBitmap.getHeight() * (512.0 / myBitmap.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 512, nh, true);
            Bitmap bitmap = FileSelection.fixedOrientation(scaled,f.getAbsolutePath());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
            Log.d("imageEncoded", imageEncoded);
            imageEncoded = resizeBase64Image(imageEncoded);
            Log.d("convertedEncoded", imageEncoded);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageEncoded;
    }

    public static Bitmap stringToBitmapImage(String stringImage) {
        Bitmap bitmap = null;
        try {
            byte[] decodedString = Base64.decode(stringImage, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return bitmap;
    }

    public static void setImagViewAsBitmapAndCircular(Context context, String profilePic, ImageView imageView) {
        try {
            Glide.with(context)
                    .load(FileSelection.stringToBitmapImage(profilePic))
                    .apply(new RequestOptions().circleCrop())
                    .into(imageView);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void setImageView(Context context, String imageStoragePath, ImageView imageView) {
        try {
            Glide.with(context)
                    .load(imageStoragePath)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.icon_profile)
                            .error(R.drawable.icon_profile)
                            .circleCrop())
                    .into(imageView);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /* Create the File */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static File createFile(String fileName) {
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Raigadshree");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e("TAG", "Oops! Failed create directory");
                return null;
            }
        }

        // Preparing media file naming convention
        // adds timestamp
        String timeStamp = new SimpleDateFormat("ddmmyyyy_HHmmss", Locale.getDefault()).format(new Date());

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + fileName + "_" + timeStamp + "." + "pdf");

        return mediaFile;
    }

    public static String compressImage(Context context, String path) {


        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(path, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(path);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static int calculateInSampleSize(@NonNull BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width lower or equal to the requested height and width.
            while ((height / inSampleSize) > reqHeight || (width / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static String resizeBase64Image(String base64image) {
        byte[] encodeByte = Base64.decode(base64image.getBytes(), Base64.DEFAULT);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length, options);


        if (image.getHeight() <= 400 && image.getWidth() <= 400) {
            return base64image;
        }
        image = Bitmap.createScaledBitmap(image, 512, 512, false);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] b = baos.toByteArray();
        System.gc();
        return Base64.encodeToString(b, Base64.NO_WRAP);

    }


    public static Bitmap fixedOrientation(Bitmap myBitmap, String path)
    {
        try
        {

            ExifInterface ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);


            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    myBitmap = rotateImage(myBitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    myBitmap = rotateImage(myBitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    myBitmap = rotateImage(myBitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    myBitmap = myBitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myBitmap;
    }
}
