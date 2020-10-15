package net.osmand.server.controllers.pub;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

import net.osmand.GPXUtilities.GPXFile;
import net.osmand.GPXUtilities.GPXTrackAnalysis;

@WebListener
@Component
public class UserSessionResources implements HttpSessionListener {

	protected static final String SESSION_GPX = "gpx";
	
	static class GPXSessionContext {
		
		List<File> tempFiles = new ArrayList<>();
		List<GPXFile> files = new ArrayList<>();
		List<GPXTrackAnalysis> analysis = new ArrayList<>();
		
	}
	
	public GPXSessionContext getGpxResources(HttpSession httpSession) {
		GPXSessionContext ctx = (GPXSessionContext) httpSession.getAttribute(SESSION_GPX);
		if (ctx == null) {
			ctx = new GPXSessionContext();
			httpSession.setAttribute(SESSION_GPX, ctx);
		}
		return ctx;
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		GPXSessionContext ctx = (GPXSessionContext) se.getSession().getAttribute(SESSION_GPX);
		if (ctx != null) {
			ctx.files.clear();
			for (File f : ctx.tempFiles) {
				f.delete();
			}
			ctx.tempFiles.clear();
		}
	}
}