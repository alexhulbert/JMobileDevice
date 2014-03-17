package com.alexhulbert.jmobiledevice.afc;

/**
 *
 * @author Taconut
 */
public interface AFCConstants {
    /**
     * Status
     */
    public static final int AFC_OP_STATUS = 0x00000001;

    /**
     * Data
     */
    public static final int AFC_OP_DATA = 0x00000002;

    /**
     * ReadDir
     */
    public static final int AFC_OP_READ_DIR = 0x00000003;

    /**
     * ReadFile
     */
    public static final int AFC_OP_READ_FILE = 0x00000004;

    /**
     * WriteFile
     */
    public static final int AFC_OP_WRITE_FILE = 0x00000005;

    /**
     * WritePart
     */
    public static final int AFC_OP_WRITE_PART = 0x00000006;

    /**
     * TruncateFile
     */
    public static final int AFC_OP_TRUNCATE = 0x00000007;

    /**
     * RemovePath
     */
    public static final int AFC_OP_REMOVE_PATH = 0x00000008;

    /**
     * MakeDir
     */
    public static final int AFC_OP_MAKE_DIR = 0x00000009;

    /**
     * GetFileInfo
     */
    public static final int AFC_OP_GET_FILE_INFO = 0x0000000a;

    /**
     * GetDeviceInfo
     */
    public static final int AFC_OP_GET_DEVINFO = 0x0000000b;

    /**
     * WriteFileAtomic (tmp file+rename)
     */
    public static final int AFC_OP_WRITE_FILE_ATOM = 0x0000000c;

    /**
     * FileRefOpen
     */
    public static final int AFC_OP_FILE_OPEN = 0x0000000d;

    /**
     * FileRefOpenResult
     */
    public static final int AFC_OP_FILE_OPEN_RES = 0x0000000e;

    /**
     * FileRefRead
     */
    public static final int AFC_OP_READ = 0x0000000f;

    /**
     * FileRefWrite
     */
    public static final int AFC_OP_WRITE = 0x00000010;

    /**
     * FileRefSeek
     */
    public static final int AFC_OP_FILE_SEEK = 0x00000011;

    /**
     * FileRefTell
     */
    public static final int AFC_OP_FILE_TELL = 0x00000012;

    /**
     * FileRefTellResult
     */
    public static final int AFC_OP_FILE_TELL_RES = 0x00000013;

    /**
     * FileRefClose
     */
    public static final int AFC_OP_FILE_CLOSE = 0x00000014;

    /**
     * FileRefSetFileSize (ftruncate)
     */
    public static final int AFC_OP_FILE_SET_SIZE = 0x00000015;

    /**
     * GetConnectionInfo
     */
    public static final int AFC_OP_GET_CON_INFO = 0x00000016;

    /**
     * SetConnectionOptions
     */
    public static final int AFC_OP_SET_CON_OPTIONS = 0x00000017;

    /**
     * RenamePath
     */
    public static final int AFC_OP_RENAME_PATH = 0x00000018;

    /**
     * SetFSBlockSize (0x800000)
     */
    public static final int AFC_OP_SET_FS_BS = 0x00000019;

    /**
     * SetSocketBlockSize (0x800000)
     */
    public static final int AFC_OP_SET_SOCKET_BS = 0x0000001A;

    /**
     * FileRefLock
     */
    public static final int AFC_OP_FILE_LOCK = 0x0000001B;

    /**
     * MakeLink
     */
    public static final int AFC_OP_MAKE_LINK = 0x0000001C;

    /**
     * set st_mtime
     */
    public static final int AFC_OP_SET_FILE_TIME = 0x0000001E;
    
    public static final int AFC_E_SUCCESS                =  0;
    public static final int AFC_E_UNKNOWN_ERROR          =  1;
    public static final int AFC_E_OP_HEADER_INVALID      =  2;
    public static final int AFC_E_NO_RESOURCES           =  3;
    public static final int AFC_E_READ_ERROR             =  4;
    public static final int AFC_E_WRITE_ERROR            =  5;
    public static final int AFC_E_UNKNOWN_PACKET_TYPE    =  6;
    public static final int AFC_E_INVALID_ARG            =  7;
    public static final int AFC_E_OBJECT_NOT_FOUND       =  8;
    public static final int AFC_E_OBJECT_IS_DIR          =  9;
    public static final int AFC_E_PERM_DENIED            = 10;
    public static final int AFC_E_SERVICE_NOT_CONNECTED  = 11;
    public static final int AFC_E_OP_TIMEOUT             = 12;
    public static final int AFC_E_TOO_MUCH_DATA          = 13;
    public static final int AFC_E_END_OF_DATA            = 14;
    public static final int AFC_E_OP_NOT_SUPPORTED       = 15;
    public static final int AFC_E_OBJECT_EXISTS          = 16;
    public static final int AFC_E_OBJECT_BUSY            = 17;
    public static final int AFC_E_NO_SPACE_LEFT          = 18;
    public static final int AFC_E_OP_WOULD_BLOCK         = 19;
    public static final int AFC_E_IO_ERROR               = 20;
    public static final int AFC_E_OP_INTERRUPTED         = 21;
    public static final int AFC_E_OP_IN_PROGRESS         = 22;
    public static final int AFC_E_INTERNAL_ERROR         = 23;
    
    public static final int AFC_E_MUX_ERROR              = 30;
    public static final int AFC_E_NO_MEM                 = 31;
    public static final int AFC_E_NOT_ENOUGH_DATA        = 32;
    public static final int AFC_E_DIR_NOT_EMPTY          = 33;
    
    /**
    * r   O_RDONLY
    */
   public static final int AFC_FOPEN_RDONLY = 0x00000001;

   /**
    * r+  O_RDWR   | O_CREAT
    */
   public static final int AFC_FOPEN_RW = 0x00000002;

   /**
    * w   O_WRONLY | O_CREAT  | O_TRUNC
    */
   public static final int AFC_FOPEN_WRONLY = 0x00000003;

   /**
    * w+  O_RDWR   | O_CREAT  | O_TRUNC
    */
   public static final int AFC_FOPEN_WR = 0x00000004;

   /**
    * a   O_WRONLY | O_APPEND | O_CREAT
    */
   public static final int AFC_FOPEN_APPEND = 0x00000005;

   /**
    * a+  O_RDWR   | O_APPEND | O_CREAT
    */
   public static final int AFC_FOPEN_RDAPPEND = 0x00000006;

   public static final int AFC_HARDLINK = 1;
   public static final int AFC_SYMLINK = 2;
   
   /**
    * shared lock
    */
   public static final int AFC_LOCK_SH = 1 | 4;

   /**
    * exclusive lock
    */
   public static final int AFC_LOCK_EX = 2 | 4;

   /**
    * unlock
    */
   public static final int AFC_LOCK_UN = 8 | 4;
   
   public static final int AFC_CMD_NONE = 0;
   
   /**
    * >
    */
   public static final int AFC_CMD_TO = 1;
   
   /**
    * >>
    */
   public static final int AFC_CMD_TOTO = 2;
   
   /**
    * <
    */
   public static final int AFC_CMD_FROM = 3;
}
