/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import java.io.ByteArrayOutputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.InputStream;
/*  6:   */ import java.io.OutputStream;
/*  7:   */ import java.io.PipedInputStream;
/*  8:   */ import java.io.PipedOutputStream;
/*  9:   */ 
/* 10:   */ public class LoopedStreams
/* 11:   */ {
/* 12: 5 */   private PipedOutputStream pipedOS = new PipedOutputStream();
/* 13: 7 */   private boolean keepRunning = true;
/* 14: 8 */   private ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream()
/* 15:   */   {
/* 16:   */     public void close()
/* 17:   */     {
/* 18:11 */       LoopedStreams.this.keepRunning = false;
/* 19:   */       try
/* 20:   */       {
/* 21:13 */         super.close();
/* 22:14 */         LoopedStreams.this.pipedOS.close();
/* 23:   */       }
/* 24:   */       catch (IOException e)
/* 25:   */       {
/* 26:19 */         System.exit(1);
/* 27:   */       }
/* 28:   */     }
/* 29:   */   };
/* 30:23 */   private PipedInputStream pipedIS = new PipedInputStream()
/* 31:   */   {
/* 32:   */     public void close()
/* 33:   */     {
/* 34:25 */       LoopedStreams.this.keepRunning = false;
/* 35:   */       try
/* 36:   */       {
/* 37:27 */         super.close();
/* 38:   */       }
/* 39:   */       catch (IOException e)
/* 40:   */       {
/* 41:32 */         System.exit(1);
/* 42:   */       }
/* 43:   */     }
/* 44:   */   };
/* 45:   */   /* 45:   */   /* 45:   */   /* 45:   */   
/* 46:   */   public LoopedStreams()
/* 47:   */     throws IOException
/* 48:   */   {
/* 49:37 */     this.pipedOS.connect(this.pipedIS);
/* 50:38 */     startByteArrayReaderThread();
/* 51:   */   }
/* 52:   */   
/* 53:   */   public InputStream getInputStream()
/* 54:   */   {
/* 55:41 */     return this.pipedIS;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public OutputStream getOutputStream()
/* 59:   */   {
/* 60:44 */     return this.byteArrayOS;
/* 61:   */   }
/* 62:   */   /* 62:   */   /* 62:   */   /* 62:   */   
/* 63:   */   private void startByteArrayReaderThread()
/* 64:   */   {
/* 65:47 */     new Thread(new Runnable()
/* 66:   */     {
/* 67:   */       public void run()
/* 68:   */       {
/* 69:49 */         while (LoopedStreams.this.keepRunning) {
/* 70:51 */           if (LoopedStreams.this.byteArrayOS.size() > 0)
/* 71:   */           {
/* 72:52 */             byte[] buffer = null;
/* 73:53 */             synchronized (LoopedStreams.this.byteArrayOS)
/* 74:   */             {
/* 75:54 */               buffer = LoopedStreams.this.byteArrayOS.toByteArray();
/* 76:55 */               LoopedStreams.this.byteArrayOS.reset();
/* 77:   */             }
/* 78:   */             try
/* 79:   */             {
/* 80:59 */               LoopedStreams.this.pipedOS.write(buffer, 0, buffer.length);
/* 81:   */             }
/* 82:   */             catch (IOException e)
/* 83:   */             {
/* 84:64 */               System.exit(1);
/* 85:   */             }
/* 86:   */           }
/* 87:   */           else
/* 88:   */           {
/* 89:   */             try
/* 90:   */             {
/* 91:70 */               Thread.sleep(1000L);
/* 92:   */             }
/* 93:   */             catch (InterruptedException localInterruptedException) {}
/* 94:   */           }
/* 95:   */         }
/* 96:   */       }
/* 97:   */     }).start();
/* 98:   */   }
/* 99:   */ }


/* Location:           E:\冒险岛DEMO\ZeroMS_079\079\dist\t079S.jar
 * Qualified Name:     gui.LoopedStreams
 * JD-Core Version:    0.7.0.1
 */