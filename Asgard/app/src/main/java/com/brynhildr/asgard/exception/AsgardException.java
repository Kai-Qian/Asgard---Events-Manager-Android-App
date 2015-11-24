package com.brynhildr.asgard.exception;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by willQian on 2015/11/12.
 */
public class AsgardException extends Exception {
    private int errorno;
    private String errormsg;
    private File writename = new File("ExceptionLog.txt");
    /**
     * Enumation of all exception.
     * @author willQian
     */
    private enum errnoNumAndMsg {
        error1("There should not be blanks.",1),
        error2("Email address is not valid.",2),
        error3("Password is too short. At least 4.",3);
        private String errorMessage;
        private int errorNum;
        /**
         * Constructor.
         * @param msg
         * @param num
         */
        private errnoNumAndMsg(String msg, int num) {
            this.errorMessage = msg;
            this.errorNum = num;
        }
        /**
         * Get the error message through error number.
         * @param num
         * @return
         */
        private static String getErrorMessage(int num) {
            for (errnoNumAndMsg c : errnoNumAndMsg.values()) {
                if (c.getErrorNum() == num) {
                    return c.errorMessage;
                }
            }
            return null;
        }
        /**
         * Get the error number.
         * @param msg
         * @return
         */
        private static int getErrorNum(String msg) {
            for (errnoNumAndMsg c : errnoNumAndMsg.values()) {
                if (c.getErrorMessage() == msg) {
                    return c.errorNum;
                }
            }
            return 0;
        }
        /**
         * Get the error message.
         * @return
         */
        private String getErrorMessage() {
            return errorMessage;
        }
        /**
         * Set the error message.
         * @param msg
         */
        private void setErrorMessage(String msg) {
            this.errorMessage = msg;
        }
        /**
         * Get the error number.
         * @return
         */
        private int getErrorNum() {
            return errorNum;
        }
        /**
         * Set the error number.
         * @param num
         */
        private void setErrorNum(int num) {
            this.errorNum = num;
        }
    }
    /**
     * Default construction.
     */
    public AsgardException() {
        super();
        try {
            writename.createNewFile();
        }catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    /**
     * Constructor.
     * @param errormsg
     */
    public AsgardException(String errormsg) {
        super();
        this.errormsg = errormsg;
//            printmyproblem();
    }
    /**
     * Constructor.
     * @param errorno
     */
    public AsgardException(int errorno) {
        super();
        this.errorno = errorno;
        this.errormsg = errnoNumAndMsg.getErrorMessage(errorno);
    }
    /**
     * Constructor.
     * @param errorno
     * @param errormsg
     */
    public AsgardException(int errorno, String errormsg) {
        super();
        this.errorno = errorno;
        this.errormsg = errormsg;
    }
    /**
     * Get the error number.
     * @return
     */
    public int getErrorno() {
        return errorno;
    }
    /**
     * Set the error number.
     * @param errorno
     */
    public void setErrorno(int errorno) {
        this.errorno = errorno;
    }
    /**
     * Get the error message.
     * @return
     */
    public String getErrormsg() {
        return errormsg;
    }
    /**
     * Set the error message.
     * @param errormsg
     */
    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
    /**
     * Log all the exception information since first time running the function.
     */
    public void logException() {
        // 1) create a java calendar instance
        Calendar calendar = Calendar.getInstance();
        // 2) get a java.util.Date from the calendar instance.
        Date now = calendar.getTime();
        String s = "Error #" + this.getErrorno() + ": " + this.getErrormsg();
        String time = now.toString();
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(writename, true));
            out.write(time + "\r\n");
            out.write(s + "\r\n");
            out.newLine();
            out.flush();
            out.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    /**
     * Print the error number and error message.
     */
    public void printmyproblem(Context o) {
        Toast toast = Toast.makeText(o, errormsg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


}

