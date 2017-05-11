package com.example.tux.mylab.camera;

/**
 * Created by dev22 on 5/8/17.
 */

interface CameraContract {
    interface View {
        /**
         * @see #changeIconPhotoVideo(int)
         */
        int STATE_PHOTO = 0;
        /**
         * @see #changeIconPhotoVideo(int)
         */
        int STATE_VIDEO = 1;

        /**
         * @return true: granted, false: denied
         */
        boolean isHaveCameraPermission();

        /**
         * request permission when not granted
         */
        void requestCameraPermission();

        /**
         * show camera
         *
         * @param isFrontCamera true: show front, false show back camera
         * @param flashMode     FLASH_MODE_AUTO (default), FLASH_MODE_ON ,FLASH_MODE_OFF
         */

        /**
         * @return true: if this device has a camera, else otherwise
         */
        boolean isHaveCamera();

        /**
         * @return true: granted, else denied
         */
        boolean isHaveWriteExternalStorage();

        /**
         * request permission write external storage to save media
         */
        void requestWriteExternalPermission();

        /**
         * capture image
         */
        void captureImage();

        /**
         * record video
         */
        void recordVideo();

        void changeIconPhotoVideo(int state_camera);

        void showFrontCamera();

        void showBackCamera();

        void refreshCameraView();

        int getFlashMode();
    }

    interface Presenter {
        /**
         * request permission for camera
         */
        void grantedCameraPermission();

        /**
         * camera permission denied by user
         */
        void cameraPermissionDenied();

        /**
         * save photo or video when have permission
         */
        void saveMedia();

        /**
         * request permission for save photo, video
         */
        void grantedWriteExternalPermission();

        /**
         * write external permission denied by user
         */
        void writeExternalPermissionDenied();

        /**
         * switch camera font/back
         */
        void switchCamera();

        /**
         * handle when click change camera mode : video | photo
         */
        void toggleVideoPhoto();
    }
}
