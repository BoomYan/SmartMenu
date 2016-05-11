//
//  MenuReviewsController.m
//  Menu
//
//  Created by 孙翔宇 on 16/5/6.
//  Copyright © 2016年 孙翔宇. All rights reserved.
//

#import "MenuReviewsController.h"
#import "SMAnnotation.h"
#import "Venue.h"
#import "UIBarButtonItem+Extension.h"

@interface MenuReviewsController ()<UIWebViewDelegate>

@property(nonatomic, strong) UIWebView *webView;

@end

@implementation MenuReviewsController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    
    UIBarButtonItem *leftItem = [UIBarButtonItem itemWithTarget:self action:@selector(back) image:@"icon_back" highImage:@"icon_back_highlighted"];
    UIBarButtonItem *rightItem = [UIBarButtonItem itemWithTarget:self action:nil image:@"icon_map" highImage:@"icon_map_highlighted"];
    self.navigationItem.rightBarButtonItem = rightItem;
    self.navigationItem.leftBarButtonItem = leftItem;
    [self initView];
    
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:self.annotation.venue.canonicalUrl]];
    [self.webView loadRequest:request];
    
}

-(void)back
{
    [self dismissViewControllerAnimated:YES completion:nil];
}

-(void)initView {
    self.webView = [[UIWebView alloc] initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, [UIScreen mainScreen].bounds.size.height)];
    self.webView.delegate = self;
    [self.view addSubview:self.webView];
}


-(void)webViewDidFinishLoad:(UIWebView *)webView
{
    if ([webView.request.URL.absoluteString isEqualToString:self.annotation.venue.canonicalUrl]){
        NSString *js = @"var buyNow =document.getElementById('tipsContainer').outerHTML;document.body.innerHTML = buyNow;alert(buyNow)";
        NSString *res = [webView stringByEvaluatingJavaScriptFromString:js];
        NSLog(@"%@", res);
    }
    
}

@end
