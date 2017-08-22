
package com.deal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.BeanMapHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * Dbutils结果集实例
 * 单行数据处理：ScalarHandler		ArrayHandler		MapHandler		BeanHandler
 * 多行数据处理：BeanListHandler		AbstractkeyedHandler(KeyedHandler	BeanMapHandler)
 * 			 AbstractListHandler(ArrayListHandler	MapListHandler	ColumnListHandler)
 * 可供扩展的类：BaseResultSetHandler
 * @author Administrator
 *
 */
public class ResultSetHandlerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ResultSetHandlerTest test = new ResultSetHandlerTest();
		//test.testColumnListHandler();
		test.testScalarHandler();
		/*
		test.testColumnListHandler();
		test.testArrayHandler();
		test.testArrayListHandler();
		test.testMapHandler();
		test.testMapListHandler();
		test.testKeyedHandler();
		test.testBeanHandler();
		test.testBeanListHandler();
		test.testBeanMapHandler();
		*/

	}
	
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/2km1h", "root", "1111");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(conn);
		return conn;
		
	}
	
	//ScalarHandler<T>用于获取结果集中第一行某列的数据并转换成T表示的实际对象。（ 该类对结果集的处理直接在 handle 方法中进行，不涉及 dbutils 库的其他类）
	public void testScalarHandler() {
		Connection conn = getConnection();
		QueryRunner qr = new QueryRunner();
		String sql = "select * from a01";
		
		try {
			//ScalarHandler的参数为空或null时，返回第一行第一列的数据
			//int i = qr.query(conn, sql, new ScalarHandler<Integer>());
			//System.out.println("Scalarhandler："+i);
			
			//ScalarHandler的参数可以是列的索引（从1开始）或是列名
			String taxiid = qr.query(conn, sql, new ScalarHandler<String>(1));
			System.out.println("车辆ID："+taxiid);
			String date = qr.query(conn, sql, new ScalarHandler<String>(2));
			System.out.println("时间:"+date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//ColumnListHandler<T>:根据列索引或列名获取结果集中某列的所有数据，并添加到ArrayList中。可以看成ScalarHandler<T>的加强版
		public void testColumnListHandler() {
			Connection conn = getConnection();
			QueryRunner qr = new QueryRunner();
			String sql = "select * from a01";
			try {
				List<String> list = (List) qr.query(conn, sql, new ColumnListHandler<String>("stuName"));
				//List<String> list = (List) qr.query(conn, sql, new ColumnListHandler<String>(2));
				for (String string : list) {
					System.out.println(string);
				}
				System.out.println(list);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	
	//ArrayHandler：把结果集中的第一行数据转成对象数组(用于获取结果集中的第一行数据，并将其封装到数组中，一列值对应一个数组元素)
	public void testArrayHandler() {
		Connection conn = getConnection();
		QueryRunner qr = new QueryRunner();
		String sql = "select * from stuInfo";
		try {
			Object result[] = qr.query(conn, sql, new ArrayHandler());
			System.out.println("ArrayHandler:"+Arrays.asList(result));
			System.out.println("ArrayHandler:"+Arrays.toString(result));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//ArrayListHandler：把结果集中的每一行数据都转成一个Object数组(处理过程等同于ArrayHandler)，再将该数组添加到ArrayList中。
	//简单点，就是将每行数据经ArrayHandler处理后，添加到ArrayListhandler中
	public void testArrayListHandler() {
		Connection conn = getConnection();
		QueryRunner qr = new QueryRunner();
		String sql = "select * from stuInfo";
		try {
			List<Object[]> list = (List) qr.query(conn, sql, new ArrayListHandler());
			for (Object[] objects : list) {
				System.out.println(Arrays.asList(objects));
				System.out.println(Arrays.toString((Object[])objects));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//KeyedHandler：用于获取所有结果集，将每行结果集转换为Map<String,Object>,并指定某列为Key。
	//可以简单认为是一个双层Map，相当于先对每行数据执行MapHandler，再为其指定Key添加到HashMap中.
	//KeyedHadler<K>中的<K>是指定的列值得类型
	public void testKeyedHandler() {
		Connection conn = getConnection();
		QueryRunner qr = new QueryRunner();
		String sql = "select * from stuInfo";
		
		try {
			//在这里，指定主键stuNo为结果Key
			//注意：也可指定其他列作为Key,如果选取的列值存在重复，则后面的会覆盖前面的，以保证HashMaP中Key值是唯一的 
			Map<Integer,Map> map = (Map)qr.query(conn, sql, new KeyedHandler("stuNo"));
			//Map<Integer,Map> map = (Map)qr.query(conn, sql, new KeyedHandler(1));
			for (Map.Entry<Integer, Map> element : map.entrySet()) {//entrySet()返回此映射中包含的映射关系的 Set 视图。
				int stuNo = element.getKey();
				
				Map<String,Object> innermap = element.getValue();
				for (Map.Entry<String, Object> innerelement : innermap.entrySet()) {
					String columnName = innerelement.getKey();
					Object value = innerelement.getValue();
					System.out.println(columnName+"="+value);
					System.out.println("-------------------");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//MapHandler:将结果集的第一行数据封装到一个Map里，Map中key是列名，value就是对应的值
	public void testMapHandler() {
		Connection conn = getConnection();
		QueryRunner qr = new QueryRunner();
		String sql = "select * from stuInfo";
		
		try {
			Map<String,Object> map = (Map)qr.query(conn, sql, new MapHandler());
			for (Map.Entry<String, Object> me : map.entrySet()) {
				System.out.println(me.getKey()+"="+me.getValue());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//MapListHandler:将结果集每行数据转换为Map(处理结果等同于MapHandler),再将Map添加到ArrayList中
	public void testMapListHandler() {
		Connection conn = getConnection();
		QueryRunner qr = new QueryRunner();
		String sql = "select * from stuInfo";
		
		try {
			List<Map> list = (List)qr.query(conn, sql, new MapListHandler());
			for (Map<String,Object> map : list) {
				System.out.println((Map<String,Object>) map);
				/*for (Map.Entry<String, Object> me : map.entrySet()) {
					System.out.println(me.getKey()+"="+me.getValue());
				}*/
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//BeanHandler<T>:将结果集的第一行数据封装到对应的JavaBean实例中
	//注意：数据库中表中字段要和JavaBean中属性保持一致
	public void testBeanHandler() {
		Connection conn = getConnection();
		QueryRunner qr = new QueryRunner();
		String sql = "select * from stuInfo";
		
		try {
			StuInfo stuInfo = qr.query(conn, sql, new BeanHandler<StuInfo>(StuInfo.class));
			System.out.println("BeanHandler:"+stuInfo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//BeanListHandler<T>:用于将结果集的每一行数据转换为JavaBean，再将这个JavaBean添加到ArrayList中,可以看成BeanHandler的高级版
	public void testBeanListHandler() {
		Connection conn = getConnection();
		QueryRunner qr = new QueryRunner();
		String sql = "select * from stuInfo";
		
		try {
			List<StuInfo> list = (List)qr.query(conn, sql, new BeanListHandler<StuInfo>(StuInfo.class));
			System.out.println("BeanListHandler:"+list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//BeanMapHandler:用于获取所有结果集，将每行结果集转换为JavaBean作为value,并指定某列为Key,封装到HashMap中。
	//相当于，对每行数据做BeanHandler一样的处理后，再指定列值为key封装到HashMap中。
	public void testBeanMapHandler() {
		Connection conn = getConnection();
		QueryRunner qr = new QueryRunner();
		String sql = "select * from stuInfo";
		
		try {
			Map<Integer,StuInfo> map = qr.query(conn, sql, new BeanMapHandler<Integer,StuInfo>(StuInfo.class, 1));
			System.out.println("BeanMapHandler:"+map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

