package megabrain.gyeongnamgyeongmae.domain.image.dto;

public enum FileType {
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png");

    private final String extension;

    FileType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static boolean isValid(String extension) {
        for (FileType imageExtension : FileType.values()) {
            if (imageExtension.getExtension().equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}