package samsoya.cameroonapp;

public final class PicturePreviewHolder {

    private static PicturePreviewHolder sInstance;
    private byte[] mCapturedPhotoData;

    // Getters & Setters
    public byte[] getCapturedPhotoData() {
        return mCapturedPhotoData;
    }

    public void setCapturedPhotoData(byte[] capturedPhotoData) {
        mCapturedPhotoData = capturedPhotoData;
    }

    // Singleton code
    public static PicturePreviewHolder getInstance() {
        if (sInstance == null) {
            sInstance = new PicturePreviewHolder();
        }
        return sInstance;
    }

    public void onCreate() {
        sInstance = this;
    }
}
