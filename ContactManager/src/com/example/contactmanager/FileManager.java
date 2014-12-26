package com.example.contactmanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Vamsi Katepalli
 * 
 * Net id: Vxk142730
 * 
 * This class is written as part of User Interface Assignment taught by 
 * Dr. John Cole.
 * start date: 10/27/2014
 * 
 * This class is used as Database layer to read and write
 * text files required for the project.Business layer will 
 * use this class for its operations like Add,Delete and Modify. 
 */
public class FileManager  {

	/**
	 * This method will add a new record in to the file.
	 * 
         * @param user
	 * @return boolean (success - true or failure- false)
	 * @throws IOException 
	 */
	public int AddUser(User user,String filePath) throws IOException{
		              
				File file = new File(filePath, "/UserContacts.txt");
				List<User> list = getAllRecords(filePath);
                FileWriter fileWriter = null;
	             // check if user already exists.
//	              if(list.indexOf(user)!=-1){
//	                  return -1;
//	              }
                //converting the object to  ; seperated fields.
                user.setFirstName(user.getFirstName().replaceAll("\n", "")); //escaping new line characters.
                user.setLastName(user.getLastName().replaceAll("\n", ""));
                String userText = user.getFirstName()+";"+user.getLastName()+";"+user.getPhoneNumber()+";"+user.getEmailAddress()+";";
                
                try {
                        fileWriter = new FileWriter(file,true);
                        fileWriter.write(userText);
                        fileWriter.write("\n");
                        fileWriter.flush();
			
				} catch (FileNotFoundException e) {
				} finally{
					if(fileWriter!=null)
						fileWriter.close();
				}
		
		return 0;
	}
	
	/**
	 * This method will delete a record from the file.
	 * 
     * @param user
	 * @return boolean (success - true or failure- false)
	 */
	public boolean DeleteUser(User user,String filePath) {
		try{
            List<User> list = getAllRecords(filePath);
            
            list.remove(list.indexOf(user));
            
            writeFile(list,filePath);
                               
            return false;
		}catch(Exception exception){
			exception.printStackTrace();
		}    
	    return false;
	}
	
	/**
	 * This method will delete a record from the file.
	 * 
     * @param user
	 * @return boolean (success - true or failure- false)
	 */
	public boolean DeleteUser(User user,String filePath,String position) {
		try{
            List<User> list = getAllRecords(filePath);
            try{
            list.remove(Integer.parseInt(position));
            }catch(Exception exception){
            	DeleteUser(user, filePath);
            }
            writeFile(list,filePath);
                               
            return false;
		}catch(Exception exception){
			exception.printStackTrace();
		}    
	    return false;
	}
	
	/**
	 * This method will modify an existing record in file.
	 *  
     * @param user
	 * @return boolean (success - true or failure- false)
	 */
	public int ModifyUser(User user,User oldUser,String filePath) throws IOException{ 
		  user.setFirstName(user.getFirstName().replaceAll("\n", ""));
          user.setLastName(user.getLastName().replaceAll("\n", ""));
            List<User> list = getAllRecords(filePath);
             // check if user already exists.
//              if(list.indexOf(user)!=-1){
//                  return -1;
//              }
            list.set(list.indexOf(oldUser),user);
            
            writeFile(list,filePath);
            
            return 0;
	}
	
	/**
	 * This method will modify an existing record in file.
	 *  
     * @param user
	 * @return boolean (success - true or failure- false)
	 */
	public int ModifyUser(User user,User oldUser,String filePath,String position) throws IOException{ 
		  user.setFirstName(user.getFirstName().replaceAll("\n", ""));
          user.setLastName(user.getLastName().replaceAll("\n", ""));
            List<User> list = getAllRecords(filePath);
             // check if user already exists.
//              if(list.indexOf(user)!=-1){
//                  return -1;
//              }
            try{
            	list.set(Integer.parseInt(position),user);
            }catch(NumberFormatException exception){
            	return ModifyUser(user, oldUser, filePath);
            }
            writeFile(list,filePath);
            
            return 0;
	}
	
	/**
	 * This method will get all existing records in file.
	 *  
	 * @return boolean (success - true or failure- false)
	 * @throws IOException 
	 */
	public List<User> getAllRecords(String filePath) throws IOException{
		return loadFile(filePath);
	}
	
	/**
	 * This method will load existing records in file.
	 *  
	 * @return List list if all users
	 * @throws IOException 
	 */
	private List<User> loadFile(String filePath) throws IOException{
		File file = new File(filePath, "/UserContacts.txt");
		BufferedReader fileReader = null;
        List<User> userList = new ArrayList<User>();
		try {
			fileReader = new BufferedReader(new FileReader(file));
			String input;
			while((input = fileReader.readLine())!= null)
			{
				userList.add(getUserObject(input));
			}
			
		} catch (FileNotFoundException e) {
		} finally{
			if(fileReader!=null)
				fileReader.close();
		}
		Collections.sort(userList);
		return userList;
	}
        
        /**
	 * This method will write the records to file.
	 *  
	 * @return 
	 * @throws IOException 
	 */
        public void writeFile(List<User> userList,String filePath) throws IOException{
            FileWriter fileWriter = new FileWriter(new File(filePath,"/UserContacts.txt"));
            
            BufferedWriter bw = new BufferedWriter(fileWriter);
            fileWriter.write("");
            for(User user: userList){
                bw.write(user.getFirstName()+";"+user.getLastName()+";"+user.getPhoneNumber()+";"+user.getEmailAddress()+";");
                bw.write("\n");
            }
            bw.close();
            fileWriter.close();
            
        }

        /**
	 * This method will convert string to user object.
	 *  
	 * @return User
	 * @throws IOException 
	 */
	private User getUserObject(String input) {
		try{
		String[] inputArr = input.split(";");
		User user = null;
		if(inputArr.length > 3)
			user = new User(inputArr[0],inputArr[1],inputArr[2],inputArr[3],0);
		else if(inputArr.length > 2)
			user = new User(inputArr[0],inputArr[1],inputArr[2],"",0);
		else if(inputArr.length > 1)
			user = new User(inputArr[0],inputArr[1],"","",0);
		else if(inputArr.length > 0)
			user = new User(inputArr[0],"","","",0);
		return user;
		}
		catch(Exception exception){
			exception.printStackTrace();
			System.out.println(exception.getMessage());
			return null;
		}
		
	}


		/**
		 * @param filesDir
		 * @param user
		 * @return User
		 * This method gets the user record.
		 */
		public User getRecord(String filesDir,User user) {
			try {
				List<User> list = loadFile(filesDir);
				int index = list.indexOf(user);
				return list.get(index);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return user;
			
		}
		
		/**
		 * @param filesDir
		 * @param user
		 * @return User
		 * This method gets the user record.
		 */
		public User getRecord(String filesDir,User user,String position) {
			try {
				List<User> list = loadFile(filesDir);
				int index = list.indexOf(user);
				int intPosition = Integer.parseInt(position);
				if(index==intPosition){
					return list.get(index);
				}else{
					return list.get(intPosition);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}catch(NumberFormatException exception){
				exception.printStackTrace();
				return getRecord(filesDir, user);
			}
			return user;
			
		}
	
	
}
