package com.game.http.netty;

import com.game.http.controller.BaseHttpController;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedStream;
import io.netty.util.CharsetUtil;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by Administrator on 2016/12/29.
 */
public class MyHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    public MyHttpHandler() {
    }

    private MockHttpServletRequest createServletRequest(FullHttpRequest fullHttpRequest) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(fullHttpRequest.getUri()).build();

        //MockHttpServletRequest servletRequest = new MockHttpServletRequest(this.servletContext);
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setRequestURI(uriComponents.getPath());
        servletRequest.setPathInfo(uriComponents.getPath());
        servletRequest.setMethod(fullHttpRequest.getMethod().name());

        if (uriComponents.getScheme() != null) {
            servletRequest.setScheme(uriComponents.getScheme());
        }
        if (uriComponents.getHost() != null) {
            servletRequest.setServerName(uriComponents.getHost());
        }
        if (uriComponents.getPort() != -1) {
            servletRequest.setServerPort(uriComponents.getPort());
        }

        for (String name : fullHttpRequest.headers().names()) {
            servletRequest.addHeader(name, fullHttpRequest.headers().get(name));
        }

        // 将post请求的参数，添加到HttpServletRrequest的parameter
        try {
            ByteBuf buf=fullHttpRequest.content();
            int readable=buf.readableBytes();
            byte[] bytes=new byte[readable];
            buf.readBytes(bytes);
            String contentStr = UriUtils.decode(new String(bytes,"UTF-8"), "UTF-8");
            for(String params : contentStr.split("&")){
                String[] para = params.split("=");
                if(para.length > 1){
                    servletRequest.addParameter(para[0],para[1]);
                } else {
                    servletRequest.addParameter(para[0],"");
                }
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            if (uriComponents.getQuery() != null) {
                String query = UriUtils.decode(uriComponents.getQuery(), "UTF-8");
                servletRequest.setQueryString(query);
            }

            for (Map.Entry<String, List<String>> entry : uriComponents.getQueryParams().entrySet()) {
                for (String value : entry.getValue()) {
                    servletRequest.addParameter(
                            UriUtils.decode(entry.getKey(), "UTF-8"),
                            UriUtils.decode(value, "UTF-8"));
                }
            }
        } catch (UnsupportedEncodingException ex) {
            // shouldn't happen
            ex.printStackTrace();
        }
        return servletRequest;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        handleHttp(ctx, req);
    }

    private void handleHttp(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception{
        if (!req.getDecoderResult().isSuccess()) {
            sendError(ctx, BAD_REQUEST);
            return;
        }
        MockHttpServletRequest servletRequest = createServletRequest(req);
        MockHttpServletResponse servletResponse = new MockHttpServletResponse();

        BaseHttpController controller = MyHttpServer.getUrlControllerMap().get(servletRequest.getRequestURI());
        if (null == controller) {
            // 404
            HttpResponse response = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.NOT_FOUND);
            InputStream contentStream = new ByteArrayInputStream(servletResponse.getContentAsByteArray());
            ctx.write(response);
            ChannelFuture writeFuture = ctx.writeAndFlush(new ChunkedStream(contentStream));
            writeFuture.addListener(ChannelFutureListener.CLOSE);
            return;
        }

        controller.doService(servletRequest, servletResponse);
        HttpResponseStatus status = HttpResponseStatus.valueOf(servletResponse.getStatus());
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, status);

        for (String name : servletResponse.getHeaderNames()) {
            for (Object value : servletResponse.getHeaderValues(name)) {
                response.headers().add(name, value);
            }
        }

        // Write the initial line and the header.
        ctx.write(response);

        InputStream contentStream = new ByteArrayInputStream(servletResponse.getContentAsByteArray());

        // Write the content and flush it.
        ChannelFuture writeFuture = ctx.writeAndFlush(new ChunkedStream(contentStream));
        writeFuture.addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        ByteBuf content = Unpooled.copiedBuffer(
                "Failure: " + status.toString() + "\r\n",
                CharsetUtil.UTF_8);

        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(
                HTTP_1_1,
                status,
                content
        );
        fullHttpResponse.headers().add(CONTENT_TYPE, "text/plain; charset=UTF-8");

        // Close the connection as soon as the error message is sent.
        ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }
}
