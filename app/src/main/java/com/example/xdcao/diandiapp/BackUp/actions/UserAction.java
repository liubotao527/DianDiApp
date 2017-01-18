package com.example.xdcao.diandiapp.BackUp.actions;

import com.example.xdcao.diandiapp.BackUp.bean.MyUser;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xdcao on 2017/1/18.
 */

public class UserAction {


    /**
     * 更新用户操作并同步更新本地的用户信息
     */
    private void updateUser() {

    }

    /**
     * 验证旧密码是否正确
     * @param
     * @return void
     */
    public static void checkPassword(String pwd) {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        final MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
        // 如果你传的密码是正确的，那么arg0.size()的大小是1，这个就代表你输入的旧密码是正确的，否则是失败的
        query.addWhereEqualTo("password", pwd );
        query.addWhereEqualTo("username", bmobUser.getUsername());
        query.findObjects(new FindListener<MyUser>() {

            @Override
            public void done(List<MyUser> object, BmobException e) {
                if(e==null){
                    System.out.print("查询密码成功:" + object.size());
                }else{
                    System.out.print("查询密码失败..." );
                }
            }

        });
    }

    /**
     * 重置密码
     */
    private void ResetPasswrod(final String email) {
        BmobUser.resetPasswordByEmail(email, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    System.out.print("重置密码请求成功，请到" + email + "邮箱进行密码重置操作");
                }else{
                    System.out.print("重置密码失败");
                }
            }
        });
    }

    /**
     * 查询用户
     */
    private void FindBmobUser() {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("username", "lucky");
        query.findObjects(new FindListener<MyUser>() {

            @Override
            public void done(List<MyUser> object, BmobException e) {
                if(e==null){
                    System.out.print("查询用户成功：" + object.size());
                }else{
                    System.out.print("没有该用户");
                }
            }

        });
    }

    /**
     * 验证邮件
     */
    private void emailVerify(final String email) {

        BmobUser.requestEmailVerify(email, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    System.out.print("请求验证邮件成功，请到" + email + "邮箱中进行激活账户。");
                }else{
                    System.out.print("请求验证邮件失败");
                }
            }

        });
    }

    private void loginByEmailPwd(String email,String pwd){
            BmobUser.loginByAccount(email, pwd, new LogInListener<MyUser>() {

            @Override
            public void done(MyUser user, BmobException e) {
                if(user!=null){
                    System.out.print(user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId()+"-"+user.getEmail());
                }
            }
        });
    }

    private void loginByPhonePwd(String phoneNum,String pwd){

        BmobUser.loginByAccount(phoneNum, pwd, new LogInListener<MyUser>() {

            @Override
            public void done(MyUser user, BmobException e) {
                if(user!=null){
                    System.out.print("登录成功");
                    System.out.print(user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId()+"-"+user.getEmail());
                }else{
                    System.out.print("错误码："+e.getErrorCode()+",错误原因："+e.getLocalizedMessage());
                }
            }
        });
    }

    /*
    请求短信验证码，模板为默认
     */
    public static void requestCheck(String phoneNum,String template) {

        BmobSMS.requestSMSCode(phoneNum, template,new QueryListener<Integer>() {

            @Override
            public void done(Integer smsId,BmobException ex) {
                if(ex==null){//验证码发送成功
                    System.out.print("短信id："+smsId);
                }
            }
        });

    }


    public static void loginByPhoneCode(String phoneNum,String smsCode){


        BmobUser.loginBySMSCode(phoneNum, smsCode, new LogInListener<MyUser>() {

            @Override
            public void done(MyUser user, BmobException e) {
                if(user!=null){
                    System.out.print("登录成功");
                    System.out.print(""+user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId()+"-"+user.getEmail());
                }else{
                    System.out.print("错误码："+e.getErrorCode()+",错误原因："+e.getLocalizedMessage());
                }
            }
        });

    }



    /** 一键注册登录
     * @method signOrLogin
     * @return void
     * @exception
     */
    private void signOrLogin(){
        //1、调用请求验证码接口
//		BmobSMS.requestSMSCode("18312662735", "模板名称",new QueryListener<Integer>() {
//
//			@Override
//			public void done(Integer smsId,BmobException ex) {
//				if(ex==null){//验证码发送成功
//					log("smile", "短信id："+smsId);
//				}
//			}
//
//		});
        String number = et_number.getText().toString();
        String code = et_code.getText().toString();
        //2、使用手机号和短信验证码进行一键注册登录,这步有两种方式可以选择
//		//第一种：
//		BmobUser.signOrLoginByMobilePhone(number, code,new LogInListener<MyUser>() {
//
//			@Override
//			public void done(MyUser user, BmobException e) {
//				if(user!=null){
//					toast("登录成功");
//					log(""+user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId()+"-"+user.getEmail());
//				}else{
//					toast("错误码："+e.getErrorCode()+",错误原因："+e.getLocalizedMessage());
//				}
//			}
//		});
        //第二种：这种方式比较灵活，可以在注册或登录的同时设置保存多个字段值
        final MyUser user = new MyUser();
        user.setPassword("123456");
        user.setMobilePhoneNumber("15018879340");
        addSubscription(user.signOrLogin(code, new SaveListener<MyUser>() {

            @Override
            public void done(MyUser myUser, BmobException e) {
                if(e==null){
                    toast("登录成功");
                    log(""+myUser.getAge()+"-"+myUser.getObjectId()+"-"+myUser.getEmail());
                }else{
                    loge(e);
                }
            }

        }));
    }

    /** 通过短信验证码来重置用户密码
     * @method requestSmsCode
     * @return void
     * 注：整体流程是先调用请求验证码的接口获取短信验证码，随后调用短信验证码重置密码接口来重置该手机号对应的用户的密码
     */
    private void resetPasswordBySMS(){
        //1、请求短信验证码
//		BmobSMS.requestSMSCode("手机号码", "模板名称",new QueryListener<Integer>() {
//
//			@Override
//			public void done(Integer smsId,BmobException ex) {
//				if(ex==null){//验证码发送成功
//					log("短信id："+smsId);
//				}
//			}
//		});
        String code = et_code.getText().toString();
        //2、重置的是绑定了该手机号的账户的密码
        addSubscription(BmobUser.resetPasswordBySMSCode(code,"1234567", new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    toast("密码重置成功");
                }else{
                    toast("错误码："+e.getErrorCode()+",错误原因："+e.getLocalizedMessage());
                }
            }
        }));
    }

    /**修改当前用户密码
     * @return void
     * @exception
     */
    private void updateCurrentUserPwd(){
        addSubscription(BmobUser.updateCurrentUserPassword("旧密码", "新密码", new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    toast("密码修改成功，可以用新密码进行登录");
                }else{
                    loge(e);
                }
            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}



}
