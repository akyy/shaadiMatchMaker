package com.shaadi.match.maker.utils.permissionManager;

/**
 * Created by ajay
 */
public interface PermissionResult {

    void permissionGranted();

    void permissionDenied();

    void permissionForeverDenied();

}
