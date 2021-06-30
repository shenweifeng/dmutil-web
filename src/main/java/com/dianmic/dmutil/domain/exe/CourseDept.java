package com.dianmic.dmutil.domain.exe;

import com.dianmic.dmutil.domain.BaseModel;

/**
 * 
 * 
 * @date 2019年3月27日
 * 
 * @author swf
 *
 * @Description
 *
 */
public class CourseDept extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 6280548286128369496L;

    private String            deptNo;
    private String            deptName;
    private String            parentDeptNo;
    private int               level;
    private String            path;

    private String            dirNo;
    private String            dirName;

    public boolean check() {
        return false;
    }

    public CourseDept() {
        super();
    }

    public CourseDept(String deptNo, String deptName) {
        super();
        this.deptNo = deptNo;
        this.deptName = deptName;
    }

    public CourseDept(String deptNo, String deptName, String parentDeptNo, int level, String path) {
        super();
        this.deptNo = deptNo;
        this.deptName = deptName;
        this.parentDeptNo = parentDeptNo;
        this.level = level;
        this.path = path;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getParentDeptNo() {
        return parentDeptNo;
    }

    public void setParentDeptNo(String parentDeptNo) {
        this.parentDeptNo = parentDeptNo;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDirNo() {
        return dirNo;
    }

    public void setDirNo(String dirNo) {
        this.dirNo = dirNo;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

}
