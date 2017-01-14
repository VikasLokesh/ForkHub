package com.github.mobile;

import java.io.File;

public class CreateDirectory {
    public void createDirectory(final File dir) {
        if (dir != null && !dir.exists())
            dir.mkdirs();
    }
}
