package cn.cpoet.ideas.util;

import cn.cpoet.ideas.constant.FileBuildTypeExtEnum;
import cn.cpoet.ideas.constant.OSExplorerConst;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.util.OS;

/**
 * 文件操作工具
 *
 * @author CPoet
 */
public abstract class FileUtil {

    private FileUtil() {
    }

    /**
     * 在资源管理器中打开目录
     *
     * @param path 目录路径
     */
    public static void openFolder(String path) {
        if (OS.isLinux()) {
            if (!OSUtil.execCommand(OSExplorerConst.LINUX_GNOME, path)
                    && !OSUtil.execCommand(OSExplorerConst.LINUX_NAUTILUS, path)) {
                OSUtil.execCommand(OSExplorerConst.LINUX_KDE, path);
            }
        } else if (OS.isMacOSX()) {
            OSUtil.execCommand(OSExplorerConst.MACOS, path);
        } else {
            OSUtil.execCommand(OSExplorerConst.WINDOWS, path);
        }
    }

    /**
     * 在资源管理器中选中文件
     *
     * @param file 文件
     */
    public static void selectFile(VirtualFile file) {
        String filePath = FilenameUtils.separatorsToSystem(file.getPath());
        selectFile(filePath);
    }

    /**
     * 在资源管理器中选中文件
     *
     * @param filePath 文件路径
     */
    public static void selectFile(String filePath) {
        if (OS.isLinux()) {
            OSUtil.execCommand(OSExplorerConst.LINUX_GNOME, filePath);
        } else if (OS.isMacOSX()) {
            OSUtil.execCommand(OSExplorerConst.MACOS, filePath);
        } else {
            OSUtil.execCommand(OSExplorerConst.WINDOWS, "/e,/select," + filePath);
        }
    }

    /**
     * 判断是否是子路径
     *
     * @param parent 父路径
     * @param child  子路径
     * @return 是否为子路径
     */
    public static boolean isFileChild(VirtualFile parent, VirtualFile child) {
        if (parent != null && child != null) {
            while (child != null && !parent.equals(child)) {
                child = child.getParent();
            }
        }
        return parent == null || child != null;
    }

    /**
     * 获取文件的输出路径
     *
     * @param root 输出根目录
     * @param file 源文件路径
     * @return 输出路径
     */
    public static String getOutputFilePath(VirtualFile root, VirtualFile file) {
        String filePath = getRelativePath(root.getPath(), file.getPath());
        String ext = FileBuildTypeExtEnum.findBuildExt(FilenameUtils.getExtension(filePath));
        if (StringUtils.isNotEmpty(ext)) {
            filePath = FilenameUtils.removeExtension(filePath) + FilenameUtils.EXTENSION_SEPARATOR + ext;
        }
        return filePath;
    }

    /**
     * 获取相对路径
     *
     * @param rootPath 根路径
     * @param filePath 文件路径
     * @return 相对路径
     */
    public static String getRelativePath(String rootPath, String filePath) {
        return filePath.substring(rootPath.length());
    }

    /**
     * 获取文件实例
     *
     * @param root     文件根目录
     * @param filePath 文件路径
     * @return 文件实例
     */
    public static VirtualFile getFileInRoot(VirtualFile root, String filePath) {
        if (root != null) {
            return root.findFileByRelativePath(filePath);
        }
        return null;
    }

    /**
     * 移出路径开始的分隔符
     *
     * @param path 路径
     * @return 移出路径开始分隔符的路径
     */
    public static String removeStartSeparator(String path) {
        if (path != null && !path.isEmpty()) {
            if (path.startsWith("/")) {
                return path.substring(1);
            }
            if (path.startsWith("\\")) {
                return path.substring(2);
            }
        }
        return path;
    }
}
