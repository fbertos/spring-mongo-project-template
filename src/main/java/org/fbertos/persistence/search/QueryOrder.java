package org.fbertos.persistence.search;

public class QueryOrder {
   public enum Direction {
	    ASCENDING,
	    DESCENDING
   }
   
   private String column;
   private Direction direction;
   
   public String getColumn() {
	   return column;
   }
   
   public void setColumn(String column) {
	   this.column = column;
   }
   
   public Direction getDirection() {
	   return direction;
   }
	
   public void setDirection(Direction direction) {
	   this.direction = direction;
   }
   
   public static QueryOrder parseQueryOrder(String s) {
	   QueryOrder order = new QueryOrder();
	   order.setDirection(Direction.ASCENDING);
	   String[] tmp = s.split(" ");
	   order.setColumn(tmp[0].trim());
	   
	   if (tmp.length > 1) {
		   String o = tmp[1].trim().toUpperCase();
		   
		   if ("DESC".equals(o) || "DESCENDING".equals(o))
			   order.setDirection(Direction.DESCENDING);
	   }
	   
	   return order;
   }
}